import { apiFetch } from './client';

export interface CalorieRequest {
  foodName: string;
  calories: number;
}

export interface CalorieResponse {
  id: number;
  foodName: string;
  calories: number;
  date: string;
}

export async function addEntry(data: CalorieRequest): Promise<CalorieResponse> {
  return apiFetch<CalorieResponse>('/api/calorie/add', { method: 'POST', body: data });
}

export async function getToday(): Promise<CalorieResponse[]> {
  return apiFetch<CalorieResponse[]>('/api/calorie/getToday', { method: 'GET' });
}

export async function getAll(): Promise<CalorieResponse[]> {
  return apiFetch<CalorieResponse[]>('/api/calorie/getAll', { method: 'GET' });
}

export async function deleteEntry(id: number): Promise<void> {
  await apiFetch(`/api/calorie/delete/${id}`, { method: 'DELETE' });
}

export async function updateEntry(id: number, data: CalorieRequest): Promise<CalorieResponse> {
  return apiFetch<CalorieResponse>(`/api/calorie/update/${id}`, { method: 'PUT', body: data });
}

export async function getTodayTotal(): Promise<number> {
  return apiFetch<number>('/api/calorie/today/total', { method: 'GET' });
}
