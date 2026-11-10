import { useState, type FormEvent } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Activity, Eye, EyeOff, Lock, Mail } from "lucide-react";
import axios from "axios";
import { loginUser } from "../../services/authService";
import { useAuth } from "../../context/AuthContext";

export default function Login() {
  const navigate = useNavigate();
  const { login } = useAuth();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleLogin = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    setError("");
    setLoading(true);

    try {
      const response = await loginUser({ email, password });

      login(response.token);
      navigate("/dashboard");
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        setError(
          err.response?.data?.message ||
          "Invalid email or password."
        );
      } else {
        setError("Something went wrong. Please try again.");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-[#080D19] flex items-center justify-center px-4">
      <div className="w-full max-w-md bg-[#111827] border border-gray-800 rounded-2xl p-8 shadow-2xl">

        <div className="flex justify-center mb-6">
          <div className="bg-blue-600/20 p-4 rounded-xl">
            <Activity size={32} className="text-blue-500" />
          </div>
        </div>

        <h1 className="text-3xl font-bold text-white text-center">
          DevPulse
        </h1>

        <p className="text-gray-400 text-center mt-2 mb-8">
          Sign in to monitor your applications
        </p>

        <form onSubmit={handleLogin} className="space-y-5">

          <div>
            <label className="block text-gray-300 mb-2 text-sm">
              Email Address
            </label>

            <div className="relative">
              <Mail className="absolute left-3 top-3.5 text-gray-500" size={18} />

              <input
                type="email"
                required
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="you@example.com"
                className="w-full bg-[#080D19] border border-gray-700 rounded-lg py-3 pl-11 pr-4 text-white outline-none focus:border-blue-500"
              />
            </div>
          </div>

          <div>
            <label className="block text-gray-300 mb-2 text-sm">
              Password
            </label>

            <div className="relative">
              <Lock className="absolute left-3 top-3.5 text-gray-500" size={18} />

              <input
                type={showPassword ? "text" : "password"}
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Enter your password"
                className="w-full bg-[#080D19] border border-gray-700 rounded-lg py-3 pl-11 pr-12 text-white outline-none focus:border-blue-500"
              />

              <button
                type="button"
                onClick={() => setShowPassword(!showPassword)}
                className="absolute right-3 top-3.5 text-gray-500"
              >
                {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
              </button>
            </div>
          </div>

          {error && (
            <p className="text-red-400 text-sm">{error}</p>
          )}

          <button
            type="submit"
            disabled={loading}
            className="w-full bg-blue-600 hover:bg-blue-700 disabled:opacity-50 text-white font-semibold py-3 rounded-lg transition"
          >
            {loading ? "Signing in..." : "Sign In"}
          </button>

        </form>

        <p className="text-gray-400 text-center mt-6 text-sm">
          Don't have an account?{" "}
          <Link to="/register" className="text-blue-400 hover:text-blue-300">
            Create account
          </Link>
        </p>

      </div>
    </div>
  );
}