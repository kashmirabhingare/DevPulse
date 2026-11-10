import { useState, type FormEvent } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Activity, User, Mail, Lock } from "lucide-react";
import axios from "axios";
import { registerUser } from "../../services/authService";

export default function Register() {
  const navigate = useNavigate();

  const [fullName, setFullName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleRegister = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      await registerUser({ fullName, email, password });
      navigate("/login", {
        state: { message: "Registration successful. Please log in." },
      });
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        setError(
          err.response?.data?.message || "Registration failed."
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
          Create Account
        </h1>

        <p className="text-gray-400 text-center mt-2 mb-8">
          Get started with DevPulse
        </p>

        <form onSubmit={handleRegister} className="space-y-5">

          <div>
            <label className="block text-gray-300 mb-2 text-sm">
              Full Name
            </label>

            <div className="relative">
              <User className="absolute left-3 top-3.5 text-gray-500" size={18} />

              <input
                required
                value={fullName}
                onChange={(e) => setFullName(e.target.value)}
                placeholder="Your full name"
                className="w-full bg-[#080D19] border border-gray-700 rounded-lg py-3 pl-11 pr-4 text-white outline-none focus:border-blue-500"
              />
            </div>
          </div>

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
                type="password"
                required
                minLength={6}
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Create a password"
                className="w-full bg-[#080D19] border border-gray-700 rounded-lg py-3 pl-11 pr-4 text-white outline-none focus:border-blue-500"
              />
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
            {loading ? "Creating account..." : "Create Account"}
          </button>

        </form>

        <p className="text-gray-400 text-center mt-6 text-sm">
          Already have an account?{" "}
          <Link to="/login" className="text-blue-400 hover:text-blue-300">
            Sign in
          </Link>
        </p>

      </div>
    </div>
  );
}