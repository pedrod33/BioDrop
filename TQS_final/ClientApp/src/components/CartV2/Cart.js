import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Title from '../Profile/Title';
import { Button } from 'bootstrap';

// Generate Order Data
function createData(id, name, shipTo, quantity,amount) {
  return { id,  name, shipTo, quantity,  amount };
}

const rows = [
  createData(0, 'Batata', 'Porto, PT',3,  20.40),
  createData(1,  'Couve', 'Porto, PT',4,  6.99),
  createData(2,  'Batata', 'Porto, PT',5,  10.81),
  createData(3,'Cenoura', 'Porto, PT',6,  4.39),
  createData(4,  'Abacaxi', 'Porto, PT',7, 2.79),
];

function preventDefault(event) {
  event.preventDefault();
}

const useStyles = makeStyles((theme) => ({
  seeMore: {
    marginTop: theme.spacing(3),
  },
}));

export default function Cart() {
  const classes = useStyles();
  return (
    <React.Fragment>
      <Title>Shopping Cart</Title>
      <Table size="small">
        <TableHead>
          <TableRow>
             
         
            <TableCell>Name</TableCell>
            <TableCell>Ship To</TableCell>
            <TableCell align="right">Quantity</TableCell>
            <TableCell align="right">Sale Amount</TableCell>
           
            <TableCell align="right"></TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow key={row.id}>
           
              <TableCell>{row.name}</TableCell>
              <TableCell>{row.shipTo}</TableCell>
              <TableCell align="right">{row.quantity}</TableCell>
              <TableCell align="right">{row.amount}</TableCell>
              <TableCell align="right"></TableCell>
            </TableRow>
          ))}    
        </TableBody>

      </Table>
      <button style={{fontWeight: 'bold',
                                    fontSize: '0.75rem',
                                    color: 'white',
                                    backgroundColor: 'grey',
                                    borderRadius: 8,
                                    padding: '3px 10px',
                                    display: 'inline-block',
                                    backgroundColor: 'black',
                                    marginTop:20}}>checkout</button>
    </React.Fragment>
  );
}