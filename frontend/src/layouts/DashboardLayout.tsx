import { NavLink, Outlet, useNavigate } from "react-router-dom";
import {
  Activity,
  LayoutDashboard,
  FolderKanban,
  FileText,
  Settings,
  LogOut,
} from "lucide-react";
import { useAuth } from "../context/AuthContext";

export default function DashboardLayout() {
  const { logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  const navItems = [
    { name: "Overview", path: "/dashboard", icon: LayoutDashboard },
    { name: "Projects", path: "/projects", icon: FolderKanban },
    { name: "Logs", path: "/logs", icon: FileText },
    { name: "Settings", path: "/settings", icon: Settings },
  ];

  return (
    <div className="min-h-screen bg-[#080D19] text-white flex">

      <aside className="w-64 bg-[#111827] border-r border-gray-800 flex flex-col">

        <div className="p-6 flex items-center gap-3 border-b border-gray-800">
          <Activity className="text-blue-500" size={28} />
          <span className="text-xl font-bold">DevPulse</span>
        </div>

        <nav className="flex-1 p-4 space-y-2">
          {navItems.map((item) => {
            const Icon = item.icon;

            return (
              <NavLink
                key={item.path}
                to={item.path}
                end={item.path === "/dashboard"}
                className={({ isActive }) =>
                  `flex items-center gap-3 px-4 py-3 rounded-lg transition ${
                    isActive
                      ? "bg-blue-600 text-white"
                      : "text-gray-400 hover:bg-gray-800 hover:text-white"
                  }`
                }
              >
                <Icon size={19} />
                {item.name}
              </NavLink>
            );
          })}
        </nav>

        <button
          onClick={handleLogout}
          className="flex items-center gap-3 p-5 text-gray-400 hover:text-red-400 border-t border-gray-800"
        >
          <LogOut size={19} />
          Logout
        </button>

      </aside>

      <main className="flex-1 min-w-0">
        <header className="h-16 border-b border-gray-800 flex items-center px-8">
          <h2 className="font-semibold text-lg">Application Monitor</h2>
        </header>

        <div className="p-8">
          <Outlet />
        </div>
      </main>

    </div>
  );
}