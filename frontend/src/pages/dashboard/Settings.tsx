import { useNavigate } from "react-router-dom";
import { Settings as SettingsIcon, FolderKanban, KeyRound } from "lucide-react";

export default function Settings() {
  const navigate = useNavigate();
  const projectId = localStorage.getItem("devpulse_project_id");

  const clearProject = () => {
    localStorage.removeItem("devpulse_project_id");
    navigate("/projects");
  };

  return (
    <div className="space-y-8">

      <div>
        <h1 className="text-2xl font-bold">Settings</h1>
        <p className="text-gray-400 mt-1">
          Manage your DevPulse workspace.
        </p>
      </div>

      <div className="bg-[#111827] border border-gray-800 rounded-xl p-6">
        <div className="flex items-center gap-3 mb-5">
          <FolderKanban className="text-blue-400" />
          <h2 className="text-lg font-semibold">Active Project</h2>
        </div>

        <p className="text-gray-400 text-sm">Selected project ID</p>

        <p className="text-xl font-semibold mt-2">
          {projectId || "No project selected"}
        </p>

        <button
          onClick={clearProject}
          disabled={!projectId}
          className="mt-5 bg-gray-800 hover:bg-gray-700 disabled:opacity-40 px-4 py-2 rounded-lg"
        >
          Change Project
        </button>
      </div>

      <div className="bg-[#111827] border border-gray-800 rounded-xl p-6">
        <div className="flex items-center gap-3 mb-4">
          <KeyRound className="text-blue-400" />
          <h2 className="text-lg font-semibold">API Key Management</h2>
        </div>

        <p className="text-gray-400 text-sm">
          Your project API keys will be managed here. They will allow
          external applications to send logs to DevPulse.
        </p>

        <p className="text-yellow-400 text-sm mt-4">
          API key display and regeneration are not connected yet.
        </p>
      </div>

      <div className="bg-[#111827] border border-gray-800 rounded-xl p-6">
        <div className="flex items-center gap-3">
          <SettingsIcon className="text-blue-400" />
          <h2 className="text-lg font-semibold">Workspace</h2>
        </div>

        <p className="text-gray-400 text-sm mt-3">
          DevPulse application monitoring workspace.
        </p>
      </div>

    </div>
  );
}