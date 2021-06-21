import React from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Title from './Title';

// Generate Order Data
function createData(id, name, mail, address, celphone) {
  return { id, name, mail, address, celphone};
}

const rows = [
  createData(0, 'Joao Lorem', 'joaolorem@ua.pt', 'Porto, PT', '9123456789'),
];


export default function ProfileSettings() {
  return (
    <React.Fragment>
      <Title>My Profile</Title>
      <Table size="large">
        <TableHead>
          <TableRow>
            <TableCell>Name</TableCell>
            <TableCell>Email</TableCell>
            <TableCell>Address</TableCell>
            <TableCell>Cellphone</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow key={row.id}>
              <TableCell>{row.name}</TableCell>
              <TableCell>{row.mail}</TableCell>
              <TableCell>{row.address}</TableCell>
              <TableCell>{row.celphone}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </React.Fragment>
  );
}