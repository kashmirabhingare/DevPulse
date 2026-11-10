import api from "./api";

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  fullName: string;
  email: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  message: string;
}

export interface ApiResponse {
  success: boolean;
  message: string;
}

export const loginUser = async (
  credentials: LoginRequest
): Promise<AuthResponse> => {
  const response = await api.post<AuthResponse>(
    "/api/auth/login",
    credentials
  );

  localStorage.setItem("devpulse_token", response.data.token);

  return response.data;
};

export const registerUser = async (
  userData: RegisterRequest
): Promise<ApiResponse> => {
  const response = await api.post<ApiResponse>(
    "/api/auth/register",
    userData
  );

  return response.data;
};

export const logoutUser = () => {
  localStorage.removeItem("devpulse_token");
};