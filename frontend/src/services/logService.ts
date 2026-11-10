import api from "./api";

export interface LogEntry {
  id: number;
  level: string;
  service: string;
  message: string;
  stackTrace?: string;
  timestamp: string;
}

export interface LogPage {
  content: LogEntry[];
  totalPages: number;
  totalElements: number;
  number: number;
  size: number;
}

export interface LogFilters {
  level?: string;
  service?: string;
  page?: number;
  size?: number;
}

export const getLogs = async (
  projectId: number,
  filters: LogFilters = {}
): Promise<LogPage> => {
  const response = await api.get<LogPage>(
    `/api/logs/${projectId}`,
    { params: filters }
  );

  return response.data;
};