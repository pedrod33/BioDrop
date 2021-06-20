import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Title from './Title';

// Generate Order Data
function createData(id, date, name, shipTo, amount) {
  return { id, date, name, shipTo,  amount };
}

const rows = [
  createData(0, '16 Mar, 2019', 'Pingo Doce', 'Porto, PT',  20.40),
  createData(1, '16 Mar, 2019', 'Continent', 'Porto, PT',  6.99),
  createData(2, '16 Mar, 2019', 'Pingo Doce', 'Porto, PT',  10.81),
  createData(3, '16 Mar, 2019', 'Auchan', 'Porto, PT',  4.39),
  createData(4, '15 Mar, 2019', 'Pingo Doce', 'Porto, PT', 2.79),
];

function preventDefault(event) {
  event.preventDefault();
}

const useStyles = makeStyles((theme) => ({
  seeMore: {
    marginTop: theme.spacing(3),
  },
}));

export default function Pending() {
  const classes = useStyles();
  return (
    <React.Fragment>
      <Title>Pending Orders</Title>
      <Table size="small">
        <TableHead>
          <TableRow>
             
            <TableCell>Date</TableCell>
            <TableCell>Name</TableCell>
            <TableCell>Ship To</TableCell>
            <TableCell align="right">Sale Amount</TableCell>
            <TableCell align="right"></TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow key={row.id}>
              <TableCell>{row.date}</TableCell>
              <TableCell>{row.name}</TableCell>
              <TableCell>{row.shipTo}</TableCell>
              <TableCell align="right">{row.amount}</TableCell>
              <TableCell align="right"></TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </React.Fragment>
  );
}