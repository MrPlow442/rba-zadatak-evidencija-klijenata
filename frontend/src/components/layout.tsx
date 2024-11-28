import React from "react";
import {Button, Container} from "@mui/material";
import {useAuth} from "../auth/auth-provider";
import {Outlet} from "react-router";
import {Logout} from "@mui/icons-material";

export const Layout = () => {
    const { logout, isLoggedIn } = useAuth();

    return (
        <div>
            <header>
                {isLoggedIn() && (
                    <Container>
                        <Button color="error"
                                variant="contained"
                                startIcon={<Logout />}
                                onClick={logout}>
                            Odjava
                        </Button>
                    </Container>
                )}
            </header>
            <main>
                <Outlet />
            </main>
        </div>
    );
};
