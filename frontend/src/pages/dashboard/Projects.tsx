
import { useEffect, useState, type FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import {
  FolderKanban,
  Plus,
  ArrowRight,
  Copy,
  Eye,
  EyeOff,
  Check,
  KeyRound,
  Calendar,
} from "lucide-react";
import axios from "axios";

import {
  getMyProjects,
  createProject,
  type Project,
} from "../../services/projectService";

export default function Projects() {
  const navigate = useNavigate();

  const [projects, setProjects] = useState<Project[]>([]);
  const [name, setName] = useState("");
  const [loading, setLoading] = useState(true);
  const [creating, setCreating] = useState(false);
  const [showForm, setShowForm] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const [visibleKeys, setVisibleKeys] = useState<number[]>([]);
  const [copiedId, setCopiedId] = useState<number | null>(null);

  const loadProjects = async () => {
    try {
      setError("");
      const data = await getMyProjects();
      setProjects(data);
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        setError(
          err.response?.data?.message || "Unable to load projects."
        );
      } else {
        setError("Unable to load projects.");
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadProjects();
  }, []);

  const handleCreateProject = async (
    e: FormEvent<HTMLFormElement>
  ) => {
    e.preventDefault();

    if (!name.trim()) {
      setError("Please enter a project name.");
      return;
    }

    setCreating(true);
    setError("");
    setSuccess("");

    try {
      const newProject = await createProject({
        name: name.trim(),
      });

      setProjects((previous) => [
        newProject,
        ...previous,
      ]);

      setName("");
      setShowForm(false);
      setSuccess("Project created successfully!");

      // Automatically show the newly generated API key.
      setVisibleKeys((previous) => [
        ...previous,
        newProject.id,
      ]);

    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        setError(
          err.response?.data?.message ||
          "Could not create project."
        );
      } else {
        setError("Something went wrong.");
      }
    } finally {
      setCreating(false);
    }
  };

  const toggleApiKey = (projectId: number) => {
    setVisibleKeys((previous) =>
      previous.includes(projectId)
        ? previous.filter((id) => id !== projectId)
        : [...previous, projectId]
    );
  };

  const copyApiKey = async (
    apiKey: string,
    projectId: number
  ) => {
    try {
      await navigator.clipboard.writeText(apiKey);
      setCopiedId(projectId);

      setTimeout(() => {
        setCopiedId((current) =>
          current === projectId ? null : current
        );
      }, 2000);
    } catch {
      setError("Unable to copy API key. Please copy it manually.");
    }
  };

  const openProject = (
    projectId: number,
    destination: string
  ) => {
    localStorage.setItem(
      "devpulse_project_id",
      String(projectId)
    );

    navigate(destination);
  };

  return (
    <div className="space-y-8">

      {/* Page heading */}
      <div className="flex flex-wrap justify-between items-center gap-4">

        <div>
          <h1 className="text-2xl font-bold">
            Projects
          </h1>

          <p className="text-gray-400 mt-1">
            Manage and monitor your applications.
          </p>
        </div>

        <button
          onClick={() => {
            setShowForm(!showForm);
            setError("");
          }}
          className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 px-5 py-3 rounded-lg font-medium transition"
        >
          <Plus size={19} />
          New Project
        </button>

      </div>

      {/* Notifications */}
      {error && (
        <div className="bg-red-500/10 border border-red-500/30 text-red-400 p-4 rounded-lg">
          {error}
        </div>
      )}

      {success && (
        <div className="bg-green-500/10 border border-green-500/30 text-green-400 p-4 rounded-lg">
          {success}
        </div>
      )}

      {/* Create project form */}
      {showForm && (
        <form
          onSubmit={handleCreateProject}
          className="bg-[#111827] border border-gray-800 rounded-xl p-6 space-y-5"
        >

          <h2 className="text-xl font-semibold">
            Create a project
          </h2>

          <div>
            <label className="block text-gray-300 text-sm mb-2">
              Project Name
            </label>

            <input
              type="text"
              required
              value={name}
              onChange={(e) => setName(e.target.value)}
              placeholder="e.g. E-Commerce Backend"
              className="w-full bg-[#080D19] border border-gray-700 rounded-lg p-3 text-white outline-none focus:border-blue-500"
            />
          </div>

          <p className="text-gray-400 text-sm">
            A unique API key will be generated automatically
            when your project is created.
          </p>

          <div className="flex gap-3">

            <button
              type="submit"
              disabled={creating}
              className="bg-blue-600 hover:bg-blue-700 disabled:opacity-50 px-5 py-3 rounded-lg font-medium"
            >
              {creating ? "Creating..." : "Create Project"}
            </button>

            <button
              type="button"
              onClick={() => setShowForm(false)}
              className="bg-gray-800 hover:bg-gray-700 px-5 py-3 rounded-lg"
            >
              Cancel
            </button>

          </div>

        </form>
      )}

      {/* Project list */}
      <div>
        <div className="flex items-center justify-between mb-5">
          <h2 className="text-xl font-semibold">
            Your Projects
          </h2>

          <span className="text-gray-400 text-sm">
            {projects.length} projects
          </span>
        </div>

        {loading ? (
          <p className="text-gray-400">
            Loading projects...
          </p>
        ) : projects.length === 0 ? (

          <div className="bg-[#111827] border border-gray-800 rounded-xl p-12 text-center">
            <FolderKanban
              size={42}
              className="mx-auto text-gray-500 mb-4"
            />

            <h3 className="text-lg font-semibold">
              No projects yet
            </h3>

            <p className="text-gray-400 mt-2">
              Create your first project to start monitoring logs.
            </p>
          </div>

        ) : (

          <div className="grid grid-cols-1 xl:grid-cols-2 gap-6">

            {projects.map((project) => {
              const isVisible = visibleKeys.includes(project.id);
              const isCopied = copiedId === project.id;

              return (
                <div
                  key={project.id}
                  className="bg-[#111827] border border-gray-800 rounded-xl p-6 hover:border-blue-500/60 transition space-y-5"
                >

                  {/* Project heading */}
                  <div className="flex justify-between items-start">

                    <div className="flex items-center gap-3">

                      <div className="bg-blue-600/15 p-3 rounded-lg">
                        <FolderKanban
                          className="text-blue-400"
                          size={25}
                        />
                      </div>

                      <div>
                        <h3 className="text-lg font-semibold">
                          {project.name}
                        </h3>

                        <p className="text-gray-500 text-sm">
                          Project ID: #{project.id}
                        </p>
                      </div>

                    </div>

                    <span className="text-xs bg-green-500/10 text-green-400 px-3 py-1 rounded-full">
                      Created
                    </span>

                  </div>

                  {/* Creation date */}
                  <div className="flex items-center gap-2 text-gray-400 text-sm">
                    <Calendar size={16} />

                    {project.createdAt
                      ? new Date(project.createdAt).toLocaleDateString()
                      : "Date unavailable"}
                  </div>

                  {/* API Key */}
                  <div className="bg-[#080D19] border border-gray-700 rounded-lg p-4">

                    <div className="flex items-center gap-2 mb-3">
                      <KeyRound
                        size={17}
                        className="text-blue-400"
                      />

                      <span className="text-sm font-medium text-gray-300">
                        Project API Key
                      </span>
                    </div>

                    <div className="flex items-center gap-2">

                      <code className="flex-1 min-w-0 text-sm text-gray-300 break-all">
                        {isVisible
                          ? project.apiKey
                          : "••••••••••••••••••••••••"}
                      </code>

                      <button
                        type="button"
                        onClick={() => toggleApiKey(project.id)}
                        title={isVisible ? "Hide API key" : "Show API key"}
                        className="p-2 text-gray-400 hover:text-white"
                      >
                        {isVisible
                          ? <EyeOff size={18} />
                          : <Eye size={18} />}
                      </button>

                      <button
                        type="button"
                        onClick={() =>
                          copyApiKey(project.apiKey, project.id)
                        }
                        title="Copy API key"
                        className="p-2 text-gray-400 hover:text-blue-400"
                      >
                        {isCopied
                          ? <Check size={18} className="text-green-400" />
                          : <Copy size={18} />}
                      </button>

                    </div>

                    {isCopied && (
                      <p className="text-green-400 text-xs mt-2">
                        API key copied!
                      </p>
                    )}

                    <p className="text-yellow-400/80 text-xs mt-3">
                      Keep this key secret. Anyone who has it can submit
                      logs to this project.
                    </p>

                  </div>

                  {/* Project actions */}
                  <div className="flex flex-wrap gap-3 pt-2">

                    <button
                      onClick={() =>
                        openProject(project.id, "/dashboard")
                      }
                      className="flex-1 flex items-center justify-center gap-2 bg-blue-600 hover:bg-blue-700 px-4 py-3 rounded-lg text-sm font-medium"
                    >
                      Dashboard
                      <ArrowRight size={16} />
                    </button>

                    <button
                      onClick={() =>
                        openProject(project.id, "/logs")
                      }
                      className="flex-1 flex items-center justify-center gap-2 bg-gray-800 hover:bg-gray-700 px-4 py-3 rounded-lg text-sm font-medium"
                    >
                      View Logs
                    </button>

                  </div>

                </div>
              );
            })}

          </div>
        )}
      </div>

    </div>
  );
}
