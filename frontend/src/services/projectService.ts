
import api from "./api";

export interface Project {
  id: number;
  name: string;
  apiKey: string;
  createdAt: string;
}

export interface CreateProjectRequest {
  name: string;
}

export const getMyProjects = async (): Promise<Project[]> => {
  const response = await api.get<Project[]>("/api/projects");
  return response.data;
};

export const createProject = async (
  projectData: CreateProjectRequest
): Promise<Project> => {
  const response = await api.post<Project>(
    "/api/projects",
    projectData
  );

  return response.data;
};
