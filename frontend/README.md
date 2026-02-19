# Calorie Tracker – Frontend

React (Vite + TypeScript) frontend for the Calorie Tracker app. It talks to your API Gateway at `http://localhost:8080`.

## Setup

```bash
npm install
```

## Run (development)

1. Start your backend (API Gateway on 8080, Auth on 8081, Calorie Service on 8082).
2. Start the frontend:

```bash
npm run dev
```

The dev server runs at `http://localhost:5173` and proxies `/api` to `http://localhost:8080`, so you don’t need CORS for local development.

## Build

```bash
npm run build
```

For production, you can set `VITE_API_URL` to your API base URL if it’s not the same origin (e.g. `https://api.example.com`).

## Features

- **Login / Register** – Uses `/api/auth/login` (GET with body) and `/api/auth/register`.
- **Dashboard** – View today’s total, add/edit/delete calorie entries via `/api/calorie/*`.
- **Auth** – JWT stored in `localStorage` and sent as `Authorization: Bearer <token>` on calorie API calls.
