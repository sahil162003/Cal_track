import { useCallback, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import * as calorieApi from '../api/calorie';
import type { CalorieResponse } from '../api/calorie';
import './Dashboard.css';

export default function Dashboard() {
  const { logout, isLoggedIn } = useAuth();
  const navigate = useNavigate();
  const [entries, setEntries] = useState<CalorieResponse[]>([]);
  const [todayTotal, setTodayTotal] = useState<number>(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [foodName, setFoodName] = useState('');
  const [calories, setCalories] = useState('');
  const [editingId, setEditingId] = useState<number | null>(null);
  const [editFood, setEditFood] = useState('');
  const [editCals, setEditCals] = useState('');

  const loadData = useCallback(async () => {
    if (!isLoggedIn) return;
    setError(null);
    try {
      const [list, total] = await Promise.all([
        calorieApi.getToday(),
        calorieApi.getTodayTotal(),
      ]);
      setEntries(list);
      setTodayTotal(total);
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Failed to load data');
    } finally {
      setLoading(false);
    }
  }, [isLoggedIn]);

  useEffect(() => {
    loadData();
  }, [loadData]);

  const handleLogout = () => {
    logout();
    navigate('/login', { replace: true });
  };

  const handleAdd = async (e: React.FormEvent) => {
    e.preventDefault();
    const c = parseInt(calories, 10);
    if (!foodName.trim() || isNaN(c) || c < 0) return;
    setError(null);
    try {
      await calorieApi.addEntry({ foodName: foodName.trim(), calories: c });
      setFoodName('');
      setCalories('');
      await loadData();
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Failed to add entry');
    }
  };

  const handleDelete = async (id: number) => {
    setError(null);
    try {
      await calorieApi.deleteEntry(id);
      await loadData();
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Failed to delete');
    }
  };

  const startEdit = (entry: CalorieResponse) => {
    setEditingId(entry.id);
    setEditFood(entry.foodName);
    setEditCals(String(entry.calories));
  };

  const cancelEdit = () => {
    setEditingId(null);
    setEditFood('');
    setEditCals('');
  };

  const handleUpdate = async (e: React.FormEvent) => {
    e.preventDefault();
    if (editingId == null) return;
    const c = parseInt(editCals, 10);
    if (!editFood.trim() || isNaN(c) || c < 0) return;
    setError(null);
    try {
      await calorieApi.updateEntry(editingId, { foodName: editFood.trim(), calories: c });
      setEditingId(null);
      setEditFood('');
      setEditCals('');
      await loadData();
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Failed to update');
    }
  };

  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <h1>Calorie Tracker</h1>
        <button type="button" className="btn-logout" onClick={handleLogout}>
          Log out
        </button>
      </header>

      <section className="today-total">
        <span className="today-total-label">Today's total</span>
        <span className="today-total-value">{todayTotal} kcal</span>
      </section>

      <section className="add-form-section">
        <h2>Add entry</h2>
        <form onSubmit={handleAdd} className="add-form">
          <input
            type="text"
            placeholder="Food name"
            value={foodName}
            onChange={(e) => setFoodName(e.target.value)}
            required
          />
          <input
            type="number"
            placeholder="Calories"
            min={0}
            value={calories}
            onChange={(e) => setCalories(e.target.value)}
            required
          />
          <button type="submit">Add</button>
        </form>
      </section>

      {error && <div className="dashboard-error">{error}</div>}

      <section className="entries-section">
        <h2>Today's entries</h2>
        {loading ? (
          <p className="loading">Loading…</p>
        ) : entries.length === 0 ? (
          <p className="empty">No entries for today. Add one above.</p>
        ) : (
          <ul className="entries-list">
            {entries.map((entry) => (
              <li key={entry.id} className="entry-item">
                {editingId === entry.id ? (
                  <form onSubmit={handleUpdate} className="entry-edit-form">
                    <input
                      type="text"
                      value={editFood}
                      onChange={(e) => setEditFood(e.target.value)}
                      required
                    />
                    <input
                      type="number"
                      min={0}
                      value={editCals}
                      onChange={(e) => setEditCals(e.target.value)}
                      required
                    />
                    <button type="submit">Save</button>
                    <button type="button" onClick={cancelEdit}>Cancel</button>
                  </form>
                ) : (
                  <>
                    <span className="entry-name">{entry.foodName}</span>
                    <span className="entry-calories">{entry.calories} kcal</span>
                    <div className="entry-actions">
                      <button type="button" onClick={() => startEdit(entry)}>Edit</button>
                      <button type="button" onClick={() => handleDelete(entry.id)}>Delete</button>
                    </div>
                  </>
                )}
              </li>
            ))}
          </ul>
        )}
      </section>
    </div>
  );
}
