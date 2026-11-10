import api from "./api";

export interface DashboardData {
  totalLogs: number;
  errorLogs: number;
  warnLogs: number;
  infoLogs: number;
  debugLogs: number;
  errorPercentage: number;
  healthStatus: string;
  mostActiveService: string | null;
  latestLogTimestamp: string | null;
}

export const getDashboard = async (
  projectId: number
): Promise<DashboardData> => {
  const response = await api.get<DashboardData>(
    `/api/dashboard/${projectId}`
  );

  return response.data;
};