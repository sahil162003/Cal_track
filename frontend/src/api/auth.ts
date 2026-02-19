import { apiFetch } from './client';

export interface RegisterRequest {
  name: string;
  email: string;
  password: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

export async function register(data: RegisterRequest): Promise<void> {
  await apiFetch('/api/auth/register', { method: 'POST', body: data });
}

export async function login(data: LoginRequest): Promise<LoginResponse> {
  return apiFetch<LoginResponse>('/api/auth/login', { method: 'POST', body: data });
}
