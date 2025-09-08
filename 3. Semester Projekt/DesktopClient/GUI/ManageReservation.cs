using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Reflection.Emit;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using DesktopClient.BusinessLogicLayer;
using DesktopClient.GUI;
using DesktopClient.ModelLayer;
using Microsoft.VisualBasic;
using static System.Windows.Forms.VisualStyles.VisualStyleElement;
namespace DesktopClient

{
    public partial class ManageReservation : Form
    {
        private readonly IReservationLogic _reservationServiceAccess;
        private List<TypeQuantity>? _Reservations;
        private bool checkStatus;
        private bool checkOut;
        private bool aflyseReservation;

        public ManageReservation()
        {
            _reservationServiceAccess = new ReservationLogic();
            _Reservations = new List<TypeQuantity>();
            InitializeComponent();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {

        }

        private void Month(object sender, EventArgs e)
        {
            checkStatus = false;
            checkOut = false;

            //Den her metode skal bruges til at tjekke reservationer for i dag.

            listBox1.Items.Clear(); //Fjerner alt fra listen så vi får en ny liste. 

            List<DateTime> chosenDate = new List<DateTime>(); //Vi laver en ny list object der hedder chosenDate med DateTime som type.

            DateTime startDate = DateTime.Today; //Setter en dato for i dag. 
            DateTime endDate = DateTime.Today.AddDays(30); //Setter en dato frem til 30 dage.

            chosenDate.Add(startDate); //Datoerne bliver tilføjet i listen. 
            chosenDate.Add(endDate);


            IReservationLogic _reservationConfirmationAccess = new ReservationLogic(); //Vi laver en ny ReservationConfirmationAccess object gennem interface.


            IEnumerable<TypeQuantity> fetchedReservation = _reservationConfirmationAccess.GetAvailableRooms(chosenDate); //Gennem vores objekt kalder vi på GetAvailableRooms metode og bruge chosenDate list som parameter.


            List<TypeQuantity> _Reservations = fetchedReservation?.ToList(); //Vores hentet informationer setter _Reservations.



            if (fetchedReservation != null)
            {
                _Reservations = fetchedReservation.ToList();

            }
            else
            {
                _Reservations = new List<TypeQuantity>();
            }
            if (_Reservations != null)
            {
                if (_Reservations.Count >= 1)
                {


                    foreach (var reservation in _Reservations
                            .Where(r => !r.CheckedIn && !r.CheckedOut) //Eksluderer dem som er checket ind
                            .OrderBy(r => r.StartDate))
                    {
                        //Vi går gennem vores info som vi har fundet, og vi displayer navn, rumtype, start og slutdato.

                        string displayText = $"Guest: {reservation.GuestFullName}, Roomtype: {reservation.RoomTypeName}, Start: {reservation.StartDate:d}, End: {reservation.EndDate:d}";
                        //listBox1.Items.Add(displayText);
                        listBox1.Items.Add(reservation);
                    }


                    
                }
                else
                {
                    MessageBox.Show("No Room found.");
                }
            }

        }

        private void ListBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (listBox1.SelectedItem is not TypeQuantity selectedReservation)
                return;

            // Build the message once
            string message = BuildReservationMessage(selectedReservation);


            // Determine action based on boolean flags
            if (checkOut)
            {
                var result = MessageBox.Show(message, "Reservation Info.", MessageBoxButtons.OKCancel);
                if (result == DialogResult.OK)
                {
                    IReservationLogic reservationAccess = new ReservationLogic();
                    var checkedOutRoom = reservationAccess.CheckOut(selectedReservation.ReservationNo);

                    if (checkedOutRoom != null && checkedOutRoom.Count > 0)
                    {
                        string rooms = string.Join(", ", checkedOutRoom);
                        MessageBox.Show($"Guest is now checked out.\nAvailable Room(s): {rooms}");
                    }
                    else
                    {
                        MessageBox.Show("Check-in failed. Not enough available rooms or an error occurred.", "Error");
                    }
                }
            }
            else if (aflyseReservation == true)
            {
                var result = MessageBox.Show(message, "Reservation Info.", MessageBoxButtons.OKCancel);
                if (result == DialogResult.OK)
                {
                    IReservationLogic reservationAccess = new ReservationLogic();
                    reservationAccess.AflysReservation(selectedReservation.ReservationNo);
                    MessageBox.Show("Guest is now cancelled");
                }
                aflyseReservation = false;
            }
            else if (!checkStatus)
            {
                var result = MessageBox.Show(message, "Reservation Info.", MessageBoxButtons.OKCancel);
                if (result == DialogResult.OK)
                {
                    IReservationLogic reservationAccess = new ReservationLogic();
                    var assignedRooms = reservationAccess.CheckIn(selectedReservation.ReservationNo);

                    if (assignedRooms != null && assignedRooms.Count > 0)
                    {
                        string rooms = string.Join(", ", assignedRooms);
                        MessageBox.Show($"Guest is now checked in.\nAssigned Room(s): {rooms}", "Success");
                    }
                    else
                    {
                        MessageBox.Show("Check-in failed. Not enough available rooms or an error occurred.", "Error");
                    }
                }
            }
            else
            {
                // Just show info
                MessageBox.Show(message, "Reservation Info.", MessageBoxButtons.OK);
            }
        }
        private void SearchByName(object sender, EventArgs e)
        {
            checkStatus = false;
            checkOut = false;

            listBox1.Items.Clear();
            List<string> guestName = new List<string>(); //Vi laver en ny list object der hedder chosenDate med DateTime som type.


            string firstName = textBox1.Text.Trim();
            string lastName = textBox2.Text.Trim();

            if (string.IsNullOrWhiteSpace(firstName) && string.IsNullOrWhiteSpace(lastName))
            {
                MessageBox.Show("Please enter at least a first name or last name.");
                return;
            }

            guestName.Add(firstName);
            guestName.Add(lastName);



            IReservationLogic _reservationConfirmationAccess = new ReservationLogic(); //Vi laver en ny ReservationConfirmationAccess object gennem interface.
            IEnumerable<TypeQuantity> fetchedReservation = _reservationConfirmationAccess.GetReservationsByGuestName(guestName);

            List<TypeQuantity> _Reservations = fetchedReservation?.ToList(); //Vores hentet informationer setter _Reservations.


            if (fetchedReservation != null)
            {
                _Reservations = fetchedReservation.ToList();

            }
            else
            {
                _Reservations = new List<TypeQuantity>();
            }
            if (_Reservations != null)
            {
                if (_Reservations.Count >= 1)
                {

                    foreach (var reservation in _Reservations
                            .Where(r => !r.CheckedIn) //Eksluderer dem som er checket ind
                            .OrderBy(r => r.StartDate))
                    {
                        //Vi går gennem vores info som vi har fundet, og vi displayer navn, rumtype, start og slutdato.

                        //string displayText = $"Guest: {reservation.GuestFullName}, Roomtype: {reservation.RoomTypeName}, Start: {reservation.StartDate:d}, End: {reservation.EndDate:d}";
                        //listBox1.Items.Add(displayText);
                        listBox1.Items.Add(reservation);
                    }
                    //MessageBox.Show("Reservation should be displayed now");

                }
                else
                {
                    MessageBox.Show("No Room found.");
                }
            }
        }

        private void Day(object sender, EventArgs e)
        {
            checkStatus = false;
            checkOut = false;
            //Den her metode skal bruges til at tjekke reservationer for i dag.
            listBox1.Items.Clear(); //Fjerner alt fra listen så vi får en ny liste. 
            List<DateTime> chosenDate = new List<DateTime>(); //Vi laver en ny list object der hedder chosenDate med DateTime som type.
            DateTime startDate = DateTime.Today; //Setter en dato for i dag. 
            DateTime endDate = DateTime.Today.AddDays(1); //Setter en dato for i morgen.
            chosenDate.Add(startDate); //Datoerne bliver tilføjet i listen. 
            chosenDate.Add(endDate);
            IReservationLogic _reservationConfirmationAccess = new ReservationLogic(); //Vi laver en ny ReservationConfirmationAccess object gennem interface.
            IEnumerable<TypeQuantity> fetchedReservation = _reservationConfirmationAccess.GetAvailableRooms(chosenDate); //Gennem vores objekt kalder vi på GetAvailableRooms metode og bruge chosenDate list som parameter.
            List<TypeQuantity> _Reservations; //Vores hentet informationer setter _Reservations.
            if (fetchedReservation != null)
            {
                _Reservations = fetchedReservation.ToList();
            }
            else
            {
                _Reservations = new List<TypeQuantity>();
            }
            if (_Reservations != null)
            {
                if (_Reservations.Count >= 1)
                {
                    //Vi går gennem vores info som vi har fundet, og vi displayer navn, rumtype, start og slutdato.
                    foreach (var reservation in _Reservations
                            .Where(r => !r.CheckedIn && !r.CheckedOut) //Eksluderer dem som er checket ind
                            .OrderBy(r => r.StartDate))
                    {
                        listBox1.Items.Add(reservation);
                    }
                }
                else
                {
                    MessageBox.Show("No Room found.");
                }
            }
        }


        private void textBox3_TextChanged(object sender, EventArgs e)
        {

        }

        private void label5_Click(object sender, EventArgs e)
        {

        }

        //Hente hele reservation og display den. Display person som en del af tabelen. 
        //Hente hele reservationLine. Hente RoomType, så man kan se hvad for nogle værelser der er på reservationen.
        //Lav en seperate SQL script. Lav egen API metode. GetReservationByDate.
        //Lave joine statement. Joine Person, ReservationType, RoomType. 
        //Lave en while loop. 

        private void Search(object sender, EventArgs e) //Den her metode søger reservationer
        {
            checkStatus = false;
            checkOut = false;


            listBox1.Items.Clear(); //Fjerner alt fra listen så vi får en ny liste. 
            List<DateTime> chosenDate = new List<DateTime>(); //Vi laver en ny list object der hedder chosenDate med DateTime som type.
            var pickingDate = new PickingDate(); //Vi laver en ny objekt af PickingDate klasse
            var result = pickingDate.ShowDialog(); // ShowDialog blockerer denne window indtil vi har valgt et dato.

            if (result == DialogResult.OK)
            {
                DateTime startDate = pickingDate.StartDate;
                DateTime endDate = pickingDate.EndDate;
                chosenDate.Add(startDate); //Datoerne bliver tilføjet i listen. 
                chosenDate.Add(endDate);

                // Continue your logic here, after the user picks dates and confirms
            }
            else
            {
                // User cancelled or closed the form
            }

            IReservationLogic _reservationConfirmationAccess = new ReservationLogic(); //Vi laver en ny ReservationConfirmationAccess object gennem interface.

            IEnumerable<TypeQuantity> fetchedReservation = _reservationConfirmationAccess.GetAvailableRooms(chosenDate);

            List<TypeQuantity> _Reservations = fetchedReservation?.ToList();

            if (fetchedReservation != null)
            {
                _Reservations = fetchedReservation.ToList();

            }
            else
            {
                _Reservations = new List<TypeQuantity>();
            }
            if (_Reservations != null)
            {
                if (_Reservations.Count >= 1)
                {

                    foreach (var reservation in _Reservations.OrderBy(r => r.StartDate))
                    {
                        //Vi går gennem vores info som vi har fundet, og vi displayer navn, rumtype, start og slutdato.

                        listBox1.Items.Add(reservation);

                    }

                }
                else
                {
                    MessageBox.Show("No Room found.");
                }
            }

        }

        private void CheckedIn_Click(object sender, EventArgs e)
        {
            checkStatus = true;
            //Den her metode skal bruges til at tjekke reservationer for i dag.

            listBox1.Items.Clear(); //Fjerner alt fra listen så vi får en ny liste. 

            List<DateTime> chosenDate = new List<DateTime>(); //Vi laver en ny list object der hedder chosenDate med DateTime som type.

            DateTime startDate = DateTime.Today; //Setter en dato for i dag. 
            DateTime endDate = DateTime.Today.AddDays(30); //Setter en dato frem til 30 dage.

            chosenDate.Add(startDate); //Datoerne bliver tilføjet i listen. 
            chosenDate.Add(endDate);


            IReservationLogic _reservationConfirmationAccess = new ReservationLogic(); //Vi laver en ny ReservationConfirmationAccess object gennem interface.


            IEnumerable<TypeQuantity> fetchedReservation = _reservationConfirmationAccess.GetAvailableRooms(chosenDate); //Gennem vores objekt kalder vi på GetAvailableRooms metode og bruge chosenDate list som parameter.


            List<TypeQuantity> _Reservations; //Vores hentet informationer setter _Reservations.



            if (fetchedReservation != null)
            {
                _Reservations = fetchedReservation.ToList();

            }
            else
            {
                _Reservations = new List<TypeQuantity>();
            }
            if (_Reservations != null)
            {
                if (_Reservations.Count >= 1)
                {


                    foreach (var reservation in _Reservations
                            .Where(r => r.CheckedIn) //Eksluderer dem som er checket ind
                            .OrderBy(r => r.StartDate))
                    {
                        //Vi går gennem vores info som vi har fundet, og vi displayer navn, rumtype, start og slutdato.

                        string displayText = $"Guest: {reservation.GuestFullName}, Roomtype: {reservation.RoomTypeName}, Start: {reservation.StartDate:d}, End: {reservation.EndDate:d}";
                        //listBox1.Items.Add(displayText);
                        listBox1.Items.Add(reservation);
                    }



                }
                else
                {
                    MessageBox.Show("No Room found.");
                }
            }
        }



        private void Tilbage(object sender, EventArgs e)
        {
            var mainClient1 = new MainClient();
            mainClient1.Show();
            Hide();
        }

        private void CheckOut(object sender, EventArgs e)
        {
            checkOut = true;

            //Den her metode skal bruges til at tjekke reservationer for i dag.

            listBox1.Items.Clear(); //Fjerner alt fra listen så vi får en ny liste. 

            List<DateTime> chosenDate = new List<DateTime>(); //Vi laver en ny list object der hedder chosenDate med DateTime som type.

            DateTime startDate = DateTime.Today; //Setter en dato for i dag. 
            DateTime endDate = DateTime.Today.AddDays(1); //Setter en dato for i morgen.

            chosenDate.Add(startDate); //Datoerne bliver tilføjet i listen. 
            chosenDate.Add(endDate);


            IReservationLogic _reservationConfirmationAccess = new ReservationLogic(); //Vi laver en ny ReservationConfirmationAccess object gennem interface.


            IEnumerable<TypeQuantity> fetchedReservation = _reservationConfirmationAccess.GetAvailableRooms(chosenDate); //Gennem vores objekt kalder vi på GetAvailableRooms metode og bruge chosenDate list som parameter.


            List<TypeQuantity> _Reservations; //Vores hentet informationer setter _Reservations.



            if (fetchedReservation != null)
            {
                _Reservations = fetchedReservation.ToList();

            }
            else
            {
                _Reservations = new List<TypeQuantity>();
            }
            if (_Reservations != null)
            {
                if (_Reservations.Count >= 1)
                {

                    foreach (var reservation in _Reservations
                            .Where(r => r.CheckedIn) //Eksluderer dem som er checket ind
                            .OrderBy(r => r.StartDate))
                    {
                        //Vi går gennem vores info som vi har fundet, og vi displayer navn, rumtype, start og slutdato.

                        //string displayText = $"Guest: {reservation.GuestFullName}, Roomtype: {reservation.RoomTypeName}, Start: {reservation.StartDate:d}, End: {reservation.EndDate:d}";
                        //listBox1.Items.Add(displayText);
                        listBox1.Items.Add(reservation);
                    }
                    //MessageBox.Show("Reservation should be displayed now");

                }
                else
                {
                    MessageBox.Show("No Room found.");
                }
            }

        }

        private void AflysReservation(object sender, EventArgs e)
        {
            checkStatus = false;
            checkOut = false;
            aflyseReservation = true;
            listBox1.Items.Clear(); //Fjerner alt fra listen så vi får en ny liste. 
            List<DateTime> chosenDate = new List<DateTime>(); //Vi laver en ny list object der hedder chosenDate med DateTime som type.
            var pickingDate = new PickingDate(); //Vi laver en ny objekt af PickingDate klasse
            var result = pickingDate.ShowDialog(); // ShowDialog blockerer denne window indtil vi har valgt et dato.

            if (result == DialogResult.OK)
            {
                DateTime startDate = pickingDate.StartDate;
                DateTime endDate = pickingDate.EndDate;
                chosenDate.Add(startDate); //Datoerne bliver tilføjet i listen. 
                chosenDate.Add(endDate);

                // Continue your logic here, after the user picks dates and confirms
            }
            else
            {
                // User cancelled or closed the form
            }

            IReservationLogic _reservationConfirmationAccess = new ReservationLogic(); //Vi laver en ny ReservationConfirmationAccess object gennem interface.

            IEnumerable<TypeQuantity> fetchedReservation = _reservationConfirmationAccess.GetAvailableRooms(chosenDate);

            List<TypeQuantity> _Reservations = fetchedReservation?.ToList();

            if (fetchedReservation != null)
            {
                _Reservations = fetchedReservation.ToList();

            }
            else
            {
                _Reservations = new List<TypeQuantity>();
            }
            if (_Reservations != null)
            {
                if (_Reservations.Count >= 1)
                {

                    foreach (var reservation in _Reservations.Where((r =>!r.CheckedIn && !r.CheckedOut)).OrderBy(r => r.StartDate))
                    {
                        //Vi går gennem vores info som vi har fundet, og vi displayer navn, rumtype, start og slutdato.

                        //string displayText = $"Guest: {reservation.GuestFullName}, Roomtype: {reservation.RoomTypeName}, Start: {reservation.StartDate:d}, End: {reservation.EndDate:d}";
                        //listBox1.Items.Add(displayText);
                        listBox1.Items.Add(reservation);

                    }
                    //MessageBox.Show("Reservation should be displayed now");

                }
                else
                {
                    MessageBox.Show("No Room found.");
                }
            }

        }

        private string BuildReservationMessage(TypeQuantity res)
        {
            return $"Reservation Details:\n\n" +
                   $"Guest: {res.GuestFullName}\n" +
                   $"Room Type: {res.RoomTypeName}\n" +
                   $"Phone Number: {res.GuestPhoneNo}\n" +
                   $"Reservation Number: {res.ReservationNo}\n" +
                   $"Start: {res.StartDate:d}\n" +
                   $"End: {res.EndDate:d}\n" +
                   $"Check In Status: {res.CheckedIn:d}\n";
        }

    }
}