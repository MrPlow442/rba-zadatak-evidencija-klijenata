import {
    Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle,
    IconButton,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow
} from "@mui/material";
import {Add, Delete, Edit} from "@mui/icons-material";
import {useEffect, useState} from "react";
import {CardRequest} from "../model/card-request-types";
import {useNavigate} from "react-router";
import {RouteConstants} from "../../constants/route-constants";
import {deleteCardRequest, findAllCardRequests} from "../../service/card-request/card-request-service";
import {useSnackbar} from "notistack";

export const CardRequestList = () => {
    const [clients, setClients] = useState<CardRequest[]>([]);
    const [open, setOpen] = useState(false);
    const [selectedOib, setSelectedOib] = useState<string | null>(null);
    const navigate = useNavigate();
    const { enqueueSnackbar } = useSnackbar();

    useEffect(() => {
        findAllCardRequests()
            .then((data) => setClients(data))
            .catch((error) => enqueueSnackbar(`Error fetching clients: ${error.response.data.message}`, { variant: "error" }));
    }, [enqueueSnackbar]);


    const handleDeleteClick = (oib: string) => {
        setSelectedOib(oib);
        setOpen(true);
    }

    const handleClose = () => {
        setOpen(false);
        setSelectedOib(null);
    }

    const handleDelete = async () => {
        if (selectedOib) {
            deleteCardRequest(selectedOib)
                .then(() => setClients((prev) => prev.filter((client) => client.oib !== selectedOib)))
                .catch((error) => enqueueSnackbar(`Error deleting client: ${error.response.data.message}`, { variant: "error" }));
        }
        handleClose()
    }

    return (
        <div>
            <h1>Zahtjevi za kartice</h1>
            <Button
                variant="contained"
                color="primary"
                startIcon={<Add />}
                onClick={() => navigate(`/${RouteConstants.cardRequests}/${RouteConstants.modifiers.new}`)}
                style={{ marginBottom: "16px" }}
            >
                Kreiraj novog klijenta
            </Button>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Puno ime</TableCell>
                            <TableCell>OIB</TableCell>
                            <TableCell>Status</TableCell>
                            <TableCell>Akcije</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {clients.map((client) => (
                            <TableRow key={client.oib}>
                                <TableCell>{`${client.firstName} ${client.lastName}`}</TableCell>
                                <TableCell>{client.oib}</TableCell>
                                <TableCell>{client.status}</TableCell>
                                <TableCell>
                                    <IconButton
                                        color="primary"
                                        onClick={() => navigate(`/${RouteConstants.cardRequests}/${RouteConstants.modifiers.edit}/${client.oib}`)}
                                    >
                                        <Edit />
                                    </IconButton>
                                    <IconButton
                                        color="error"
                                        onClick={() => handleDeleteClick(client.oib)}
                                    >
                                        <Delete />
                                    </IconButton>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Confirm Deletion</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Jeste li sigurni da zelite izbrisati ovog klijenta?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={handleDelete} color="error">
                        Delete
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
};