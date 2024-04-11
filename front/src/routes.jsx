import {
  HomeIcon,
  InformationCircleIcon,
  RectangleStackIcon,
  ServerStackIcon,
  TableCellsIcon,
  UserCircleIcon,
} from "@heroicons/react/24/solid";
import {Home, Notifications, Profile, Tables} from "@/pages/dashboard";
import SignIn from "@/pages/auth/sign-in.jsx";
import SignUp from "@/pages/auth/sign-up.jsx";
//import {SignIn, SignUp} from "@/pages/auth";

const icon = {
  className: "w-5 h-5 text-inherit",
};

export const routes = [
  {
    layout: "dashboard",
    pages: [
      {
        icon: <HomeIcon {...icon} />,
        name: "dashboard",
        path: "/home",
        element: <Home/>,
      },
      {
        icon: <UserCircleIcon {...icon} />,
        name: "profile",
        path: "/profile",
        element: <Profile/>,
      },
      {
        icon: <TableCellsIcon {...icon} />,
        name: "tables",
        path: "/tables",
        element: <Tables/>,
      },
      {
        icon: <InformationCircleIcon {...icon} />,
        name: "notifications",
        path: "/notifications",
        element: <Notifications/>,
      },
    ],
  },
  {
    title: "auth pages",
    layout: "auth",
    pages: [
      {
        icon: <ServerStackIcon {...icon} />,
        name: "sign in",
        path: "/auth/sign-in",
        element: <SignIn/>,
      },
      {
        icon: <RectangleStackIcon {...icon} />,
        name: "sign up",
        path: "/sign-up",
        element: <SignUp/>,
      },
    ],
  },
];

export default routes;