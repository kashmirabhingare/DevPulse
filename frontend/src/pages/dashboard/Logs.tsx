import { useEffect, useState } from "react";
import { getLogs, type LogEntry, type LogPage } from "../../services/logService";

export default function Logs() {
  const projectId = localStorage.getItem("devpulse_project_id");

  const [logs, setLogs] = useState<LogEntry[]>([]);
  const [level, setLevel] = useState("");
  const [service, setService] = useState("");
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    if (!projectId) return;

    const loadLogs = async () => {
      setLoading(true);
      setError("");

      try {
        const result: LogPage = await getLogs(Number(projectId), {
          level: level || undefined,
          service: service || undefined,
          page,
          size: 10,
        });

        setLogs(result.content);
        setTotalPages(result.totalPages);
      } catch {
        setError("Unable to load logs.");
      } finally {
        setLoading(false);
      }
    };

    loadLogs();
  }, [projectId, level, service, page]);

  if (!projectId) {
    return (
      <p className="text-gray-400">
        Select a project from the Projects page before viewing logs.
      </p>
    );
  }

  const levelColor = (value: string) => {
    switch (value) {
      case "ERROR":
        return "text-red-400";
      case "WARN":
        return "text-yellow-400";
      case "INFO":
        return "text-blue-400";
      case "DEBUG":
        return "text-gray-400";
      default:
        return "text-white";
    }
  };

  return (
    <div className="space-y-6">

      <div>
        <h1 className="text-2xl font-bold">Log Explorer</h1>
        <p className="text-gray-400 mt-1">
          Search and inspect application logs.
        </p>
      </div>

      <div className="flex flex-wrap gap-4">

        <select
          value={level}
          onChange={(e) => {
            setLevel(e.target.value);
            setPage(0);
          }}
          className="bg-[#111827] border border-gray-700 rounded-lg p-3 text-white"
        >
          <option value="">All levels</option>
          <option value="ERROR">ERROR</option>
          <option value="WARN">WARN</option>
          <option value="INFO">INFO</option>
          <option value="DEBUG">DEBUG</option>
        </select>

        <input
          value={service}
          onChange={(e) => {
            setService(e.target.value);
            setPage(0);
          }}
          placeholder="Filter by service"
          className="bg-[#111827] border border-gray-700 rounded-lg p-3 text-white outline-none focus:border-blue-500"
        />

      </div>

      {error && <p className="text-red-400">{error}</p>}

      <div className="bg-[#111827] border border-gray-800 rounded-xl overflow-x-auto">

        <table className="w-full text-left min-w-[700px]">

          <thead className="bg-[#1A2435] text-gray-400 text-sm">
            <tr>
              <th className="p-4">Level</th>
              <th className="p-4">Service</th>
              <th className="p-4">Message</th>
              <th className="p-4">Timestamp</th>
            </tr>
          </thead>

          <tbody>
            {loading ? (
              <tr>
                <td colSpan={4} className="p-8 text-center text-gray-400">
                  Loading logs...
                </td>
              </tr>
            ) : logs.length === 0 ? (
              <tr>
                <td colSpan={4} className="p-8 text-center text-gray-400">
                  No logs found.
                </td>
              </tr>
            ) : (
              logs.map((log) => (
                <tr
                  key={log.id}
                  className="border-t border-gray-800 hover:bg-gray-800/40"
                >
                  <td className={`p-4 font-semibold ${levelColor(log.level)}`}>
                    {log.level}
                  </td>

                  <td className="p-4 text-gray-300">
                    {log.service}
                  </td>

                  <td className="p-4 text-gray-300 max-w-sm">
                    {log.message}
                  </td>

                  <td className="p-4 text-gray-400 text-sm">
                    {new Date(log.timestamp).toLocaleString()}
                  </td>
                </tr>
              ))
            )}
          </tbody>

        </table>

      </div>

      <div className="flex justify-between items-center text-sm">
        <p className="text-gray-400">
          Page {page + 1} of {Math.max(totalPages, 1)}
        </p>

        <div className="flex gap-3">
          <button
            disabled={page === 0 || loading}
            onClick={() => setPage((previous) => previous - 1)}
            className="px-4 py-2 rounded-lg bg-gray-800 disabled:opacity-40"
          >
            Previous
          </button>

          <button
            disabled={page + 1 >= totalPages || loading}
            onClick={() => setPage((previous) => previous + 1)}
            className="px-4 py-2 rounded-lg bg-blue-600 disabled:opacity-40"
          >
            Next
          </button>
        </div>
      </div>

    </div>
  );
}