import {useFormik} from "formik";
import {Button, MenuItem, Select, TextField} from "@mui/material";
import {CardRequestStatus, CardRequest} from "../model/card-request-types";
import {useNavigate, useParams} from "react-router";
import {useEffect} from "react";
import axios from "axios";
import {createCardRequest, updateCardRequest} from "../../service/card-request/card-request-service";
import {useSnackbar} from "notistack";

export const CardRequestDetails = () => {
    const {oib} = useParams<{oib?: string}>();
    const navigate = useNavigate();
    const { enqueueSnackbar } = useSnackbar();

    const formik = useFormik<CardRequest>({
        initialValues: {
            firstName: '',
            lastName: '',
            oib: '',
            status: CardRequestStatus.PENDING,
        },
        onSubmit:  (cardRequest: CardRequest) => {
            if (oib) {
                updateCardRequest(oib, cardRequest)
                    .then(() => navigate("/"))
                    .catch((error) => enqueueSnackbar(`Error updating client: ${error.response.data.message}`, { variant: 'error' }));
            } else {
                createCardRequest(cardRequest)
                    .then(() => navigate("/"))
                    .catch((error) => {
                        console.log("!!!ERROR ", error);
                        enqueueSnackbar(`Error creating client: ${error.response.data.message}`, { variant: 'error' });
                    });
            }
        }
    });

    useEffect(() => {
        if (oib) {
            axios
                .get(`http://localhost:8080/api/v1/card-request/${oib}`)
                .then((response) => {formik.setValues(response.data);})
                .catch((error) => enqueueSnackbar(`Error fetching client: ${error}`, { variant: 'error' }));
        }
    }, [enqueueSnackbar, formik, oib]);

    return (
        <div>
            <h1>{ oib ? "Uredite" : "Unesite" } podatke o zahtjevu kartice</h1>
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