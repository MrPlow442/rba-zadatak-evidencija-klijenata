import {useFormik} from "formik";
import {Button, Container, TextField} from "@mui/material";
import {useNavigate} from "react-router";
import {useSnackbar} from "notistack";
import {LoginUser} from "./auth-types";
import {useAuth} from "./auth-provider";

export const Login = () => {
    const navigate = useNavigate();
    const { enqueueSnackbar } = useSnackbar();
    const { login } = useAuth();

    const formik = useFormik<LoginUser>({
        initialValues: {
            email: '',
            password: ''
        },
        onSubmit:  (loginUser: LoginUser) => {
            login(loginUser)
                .then(() => {
                    navigate("/");
                })
                .catch((error) => {
                    enqueueSnackbar(`Error logging in: ${error.message}`, { variant: 'error' });
                });
        }
    });

    return (
        <Container>
            <h1>Prijava</h1>
            <form onSubmit={formik.handleSubmit}>
                <TextField
                    fullWidth
                    sx={{mb: 3}}
                    id="email"
                    name="email"
                    label="Email"
                    value={formik.values.email}
                    onChange={formik.handleChange}
                    onBlur={formik.handleBlur}
                    error={formik.touched.email && Boolean(formik.errors.email)}
                    helperText={formik.touched.email && formik.errors.email}
                />
                <TextField
                    fullWidth
                    sx={{mb: 3}}
                    id="password"
                    name="password"
                    label="Password"
                    type="password"
                    value={formik.values.password}
                    onChange={formik.handleChange}
                    onBlur={formik.handleBlur}
                    error={formik.touched.password && Boolean(formik.errors.password)}
                    helperText={formik.touched.password && formik.errors.password}
                />
                <Button color="primary" variant="contained" fullWidth sx={{mb: 3}} type="submit">
                    Prijava
                </Button>
            </form>
        </Container>
    );
}