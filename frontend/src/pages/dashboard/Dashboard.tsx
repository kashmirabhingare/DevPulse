import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {
  Activity,
  AlertCircle,
  AlertTriangle,
  CheckCircle,
  Info,
  Bug,
  Clock,
} from "lucide-react";
import {
  getDashboard,
  type DashboardData,
} from "../../services/dashboardService";

export default function Dashboard() {
  const projectId = localStorage.getItem("devpulse_project_id");

  const [data, setData] = useState<DashboardData | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    if (!projectId) {
      setLoading(false);
      return;
    }

    const loadDashboard = async () => {
      try {
        const result = await getDashboard(Number(projectId));
        setData(result);
      } catch {
        setError("Unable to load dashboard data.");
      } finally {
        setLoading(false);
      }
    };

    loadDashboard();
  }, [projectId]);

  if (!projectId) {
    return (
      <div className="bg-[#111827] border border-gray-800 rounded-xl p-10 text-center">
        <Activity size={40} className="mx-auto text-blue-500 mb-4" />
        <h2 className="text-xl font-semibold">Select a project</h2>
        <p className="text-gray-400 mt-2">
          Select a project to view its monitoring dashboard.
        </p>
        <Link
          to="/projects"
          className="inline-block mt-5 bg-blue-600 px-5 py-2 rounded-lg"
        >
          View Projects
        </Link>
      </div>
    );
  }

  if (loading) {
    return <p className="text-gray-400">Loading dashboard...</p>;
  }

  if (error || !data) {
    return <p className="text-red-400">{error || "No dashboard data."}</p>;
  }

  const cards = [
    { label: "Total Logs", value: data.totalLogs, icon: Activity },
    { label: "Errors", value: data.errorLogs, icon: AlertCircle },
    { label: "Warnings", value: data.warnLogs, icon: AlertTriangle },
    { label: "Info", value: data.infoLogs, icon: Info },
    { label: "Debug", value: data.debugLogs, icon: Bug },
  ];

  const healthColor =
    data.healthStatus === "HEALTHY"
      ? "text-green-400"
      : data.healthStatus === "WARNING"
      ? "text-yellow-400"
      : "text-red-400";

  return (
    <div className="space-y-8">

      <div>
        <h1 className="text-2xl font-bold">Dashboard Overview</h1>
        <p className="text-gray-400 mt-1">
          Monitor your application's activity and health.
        </p>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-3 gap-5">
        {cards.map((card) => {
          const Icon = card.icon;

          return (
            <div
              key={card.label}
              className="bg-[#111827] border border-gray-800 rounded-xl p-6"
            >
              <div className="flex justify-between items-center">
                <p className="text-gray-400">{card.label}</p>
                <Icon className="text-blue-400" size={22} />
              </div>

              <p className="text-3xl font-bold mt-5">
                {card.value}
              </p>
            </div>
          );
        })}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-5">

        <div className="bg-[#111827] border border-gray-800 rounded-xl p-6">
          <div className="flex items-center gap-3">
            <CheckCircle className={healthColor} size={25} />
            <h2 className="font-semibold text-lg">Application Health</h2>
          </div>

          <p className={`text-3xl font-bold mt-5 ${healthColor}`}>
            {data.healthStatus}
          </p>

          <p className="text-gray-400 mt-2">
            Error percentage: {Number(data.errorPercentage).toFixed(2)}%
          </p>
        </div>

        <div className="bg-[#111827] border border-gray-800 rounded-xl p-6">
          <h2 className="font-semibold text-lg">Service Activity</h2>

          <p className="text-gray-400 text-sm mt-4">
            Most active service
          </p>

          <p className="text-xl font-semibold mt-1">
            {data.mostActiveService || "No service data"}
          </p>

          <div className="flex items-center gap-2 text-gray-400 text-sm mt-5">
            <Clock size={17} />
            Latest log:{" "}
            {data.latestLogTimestamp
              ? new Date(data.latestLogTimestamp).toLocaleString()
              : "No logs received"}
          </div>
        </div>

      </div>

      <Link
        to="/logs"
        className="inline-flex items-center gap-2 text-blue-400 hover:text-blue-300"
      >
        Explore application logs →
      </Link>

    </div>
  );
}