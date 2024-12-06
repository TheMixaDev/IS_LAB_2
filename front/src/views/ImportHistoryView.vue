<template>
	<div v-if="isLoading">Загрузка импортов...</div>
	<div v-else>
		<h2>
			История импортов
		</h2>
		<button @click="promptImport" style="width: 100%; margin-bottom: 12px;">
			<font-awesome-icon :icon="['fas', 'plus']" class="icon" />
			Импортировать из csv
		</button>
		<table v-if="requests.length">
			<thead>
				<tr>
					<th>ID</th>
					<th>Пользователь</th>
					<th>Статус</th>
					<th>Количество</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="request in requests" :key="request.id">
					<td>{{ request.id }}</td>
					<td>{{ request.createdBy.username }}</td>
					<td>{{ request.status == 'ERROR' ? 'Неудачно' : 'Успешно' }}</td>
					<td>{{ request.count }}</td>
				</tr>
			</tbody>
		</table>
		<p v-else>Нет импортов.</p>
	</div>
</template>

<script>
import NetworkService from '../services/NetworkService.js';
import { mapState } from 'vuex';

export default {
	name: 'ImportHistoryView',
	data() {
		return {
			requests: [],
			isLoading: true
		};
	},
	computed: {
		...mapState(['user'])
	},
	mounted() {
		this.fetchRequests();
	},
	methods: {
		async fetchRequests() {
			this.isLoading = true;
			try {
				const response = await NetworkService.get('/music-bands/import');
				this.requests = response.data;
			} catch (error) {
				console.error('Ошибка при получении запросов:', error);
				this.$notify({ type: 'error', text: 'Не удалось получить запросы' });
			} finally {
				this.isLoading = false;
			}
		},
		promptImport() {
			const input = document.createElement('input');
			input.type = 'file';
			input.accept = '.csv';
			input.onchange = async () => {
				const file = input.files[0];
				const formData = new FormData();
				formData.append('file', file);
				try {
					await NetworkService.post('/music-bands/import', formData);
					this.$notify({ type: 'success', text: 'Импорт успешно выполнен' });
					this.fetchRequests();
				} catch (error) {
					console.error('Ошибка при импорте:', error);
					this.$notify({ type: 'error', text: error.response.data.message });
					this.fetchRequests();
				}
			};
			input.click();
		}
	}
};
</script>
