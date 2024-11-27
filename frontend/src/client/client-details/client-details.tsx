import {useFormik} from "formik";
import {Button, MenuItem, Select, TextField} from "@mui/material";
import {CardRequestStatus, Client} from "../model/client-types";
import {useNavigate, useParams} from "react-router";
import {useEffect} from "react";
import axios from "axios";

export const ClientDetails = () => {
    const {oib} = useParams<{oib?: string}>();
    const navigate = useNavigate();

    const formik = useFormik<Client>({
        initialValues: {
            firstName: '',
            lastName: '',
            oib: '',
            status: CardRequestStatus.PENDING,
        },
        onSubmit:  (values: Client) => {
            if (oib) {
                // Update existing client
                axios
                    .put(`http://localhost:8080/api/v1/clients/${oib}`, values)
                    .then(() => navigate("/"))
                    .catch((error) => console.error("Error updating client:", error));
            } else {
                // Create new client
                axios
                    .post("http://localhost:8080/api/v1/clients", values)
                    .then(() => navigate("/"))
                    .catch((error) => console.error("Error creating client:", error));
            }
        }
    });

    useEffect(() => {
        if (oib) {
            axios
                .get(`http://localhost:8080/api/v1/clients/${oib}`)
                .then((response) => {formik.setValues(response.data);})
                // .then((response) => formik.setValues(response.data))
                .catch((error) => console.error("Error fetching client:", error));
        }
    }, [oib]);

    return (
        <div>
            <h1>Unesite podatke o klijentu</h1>
            <form onSubmit={formik.handleSubmit}>
                <TextField
                    fullWidth
                    id="firstName"
                    name="firstName"
                    label="Ime"
                    value={formik.values.firstName}
                    onChange={formik.handleChange}
                    onBlur={formik.handleBlur}
                    error={formik.touched.firstName && Boolean(formik.errors.firstName)}
                    helperText={formik.touched.firstName && formik.errors.firstName}
                />
                <TextField
                    fullWidth
                    id="lastName"
                    name="lastName"
                    label="Prezime"
                    value={formik.values.lastName}
                    onChange={formik.handleChange}
                    onBlur={formik.handleBlur}
                    error={formik.touched.lastName && Boolean(formik.errors.lastName)}
                    helperText={formik.touched.lastName && formik.errors.lastName}
                />
                <TextField
                    fullWidth
                    id="oib"
                    name="oib"
                    label="Oib"
                    value={formik.values.oib}
                    onChange={formik.handleChange}
                    onBlur={formik.handleBlur}
                    error={formik.touched.oib && Boolean(formik.errors.oib)}
                    helperText={formik.touched.oib && formik.errors.oib}
                />
                <Select
                    fullWidth
                    id="status"
                    name="status"
                    label="Status"
                    value={formik.values.status}
                    onChange={formik.handleChange}
                    onBlur={formik.handleBlur}
                    error={formik.touched.status && Boolean(formik.errors.status)}
                >
                    <MenuItem value={CardRequestStatus.APPROVED}>APPROVED</MenuItem>
                    <MenuItem value={CardRequestStatus.REJECTED}>REJECTED</MenuItem>
                    <MenuItem value={CardRequestStatus.PENDING}>PENDING</MenuItem>
                </Select>
                <Button color="primary" variant="contained" fullWidth type="submit">
                    Spremi
                </Button>
                <Button color="error" variant="contained" fullWidth onClick={() => navigate(-1)}>
                    Odustani
                </Button>
            </form>
        </div>
    );
}