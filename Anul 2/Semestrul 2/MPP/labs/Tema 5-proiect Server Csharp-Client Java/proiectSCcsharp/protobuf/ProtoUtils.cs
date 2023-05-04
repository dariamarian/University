using Chat.Protocol;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using proto = Chat.Protocol;
using System.Net.Sockets;
using proiectSCcsharp.domain;

namespace protobuf
{
    internal class ProtoUtils
    {
        public static AppRequest createLoginRequest(proiectSCcsharp.domain.Angajat employee)
        {
            var myEmployee = new proto.Angajat
            {
                Id = employee.id,
                Name = employee.name,
                Username = employee.username,
                Password = employee.password
            };
            return new AppRequest
            {
                Type = AppRequest.Types.Type.Login,
                Angajat = myEmployee
            };
        }

        public static AppRequest createLogoutRequest(proiectSCcsharp.domain.Angajat employee)
        {
            var myEmployee = new proto.Angajat
            {
                Id = employee.id,
                Name = employee.name,
                Username = employee.username,
                Password = employee.password
            };
            return new AppRequest
            {
                Type = AppRequest.Types.Type.Logout,
                Angajat = myEmployee
            };
        }
        public static AppRequest createAddBiletRequest(proiectSCcsharp.domain.Bilet bilet)
        {
            proto.Bilet myTicket = new proto.Bilet
            {
                Id = bilet.id,
                IdSpectacol = bilet.idSpectacol,
                CumparatorName = bilet.cumparatorName,
                NrSeats = bilet.nrSeats
            };
            return new AppRequest
            {
                Type = AppRequest.Types.Type.AddBilet,
                Bilet = myTicket
            };
        }


        public static AppRequest createGetSpectacolRequest(long id)
        {
            return new AppRequest
            {
                Type = AppRequest.Types.Type.GetSpectacol,
                IdSpectacol = id
            };
        }

        public static AppRequest createGetAllSpectacoleRequest()
        {
            return new AppRequest
            {
                Type = AppRequest.Types.Type.GetAllSpectacole
            };
        }

        public static proiectSCcsharp.domain.Angajat GetAngajat(AppRequest request)
        {
            proiectSCcsharp.domain.Angajat employee = new proiectSCcsharp.domain.Angajat
            (
                request.Angajat.Id,
                request.Angajat.Name,
                request.Angajat.Username,
                request.Angajat.Password
            );
            return employee;
        }

        public static proiectSCcsharp.domain.Bilet GetBilet(AppRequest request)
        {
            proiectSCcsharp.domain.Bilet ticket = new proiectSCcsharp.domain.Bilet
            (
                request.Bilet.Id,
                request.Bilet.CumparatorName,
                request.Bilet.IdSpectacol,
                request.Bilet.NrSeats
            ) ;
            return ticket;
        }
        
        public static proiectSCcsharp.domain.Spectacol GetSpectacol(AppRequest request)
        {
            proiectSCcsharp.domain.Spectacol spectacol = new proiectSCcsharp.domain.Spectacol
                (
                    request.Spectacol.Id,
                    request.Spectacol.ArtistName,
                    request.Spectacol.Date,
                    request.Spectacol.Time,
                    request.Spectacol.Place,
                    request.Spectacol.AvailableSeats,
                    request.Spectacol.SoldSeats
                );
            return spectacol;
        }

        public static AppResponse createLoginResponse(proiectSCcsharp.domain.Angajat employee)
        {
            var myEmployee = new proto.Angajat
            {
                Id = employee.id,
                Name = employee.name,
                Username = employee.username,
                Password = employee.password
            };
            return new AppResponse { Type = AppResponse.Types.Type.Ok, Angajat = myEmployee };
        }

        public static AppResponse createAddBiletResponse(proiectSCcsharp.domain.Bilet ticket)
        {
            proto.Bilet myTicket = new proto.Bilet
            {
                Id = ticket.id,
                CumparatorName = ticket.cumparatorName,
                IdSpectacol = ticket.idSpectacol,
                NrSeats = ticket.nrSeats
            };
            return new AppResponse { Type = AppResponse.Types.Type.SoldBilet, Bilet = myTicket };
        }

        public static AppResponse createGetSpectacolResponse(proiectSCcsharp.domain.Spectacol spectacol)
        {
            var mySpectacol = new proto.Spectacol
            {
                Id = spectacol.id,
                ArtistName = spectacol.artistName,
                Date = spectacol.date,
                Time = spectacol.time,
                Place = spectacol.place,
                AvailableSeats = spectacol.availableSeats,
                SoldSeats = spectacol.soldSeats
            };
            return new AppResponse { Type = AppResponse.Types.Type.GetSpectacol, Spectacol = mySpectacol };
        }
        

        public static AppResponse createGetAllSpectacoleResponse(IEnumerable<proiectSCcsharp.domain.Spectacol> shows)
        {
            var showDTOList = new List<proto.Spectacol>();
            foreach (var showDTO in shows)
            {
                showDTOList.Add(new proto.Spectacol
                {
                    Id = showDTO.id,
                    ArtistName = showDTO.artistName,
                    Date = showDTO.date,
                    Time = showDTO.time,
                    Place = showDTO.place,
                    AvailableSeats = showDTO.availableSeats,
                    SoldSeats = showDTO.soldSeats,
                });
            }
            return new AppResponse { Type = AppResponse.Types.Type.GetAllSpectacole, Spectacole = { showDTOList } };
        }

        public static AppResponse createErrorResponse(string message)
        {
            return new AppResponse { Type = AppResponse.Types.Type.Error, Error = message };
        }

        public static AppResponse createOkResponse()
        {
            return new AppResponse { Type = AppResponse.Types.Type.Ok };
        }

        public static proiectSCcsharp.domain.Angajat GetAngajat(AppResponse response)
        {
            proiectSCcsharp.domain.Angajat employee = new proiectSCcsharp.domain.Angajat
            (
                response.Angajat.Id,
                response.Angajat.Name,
                response.Angajat.Username,
                response.Angajat.Password
            );
            return employee;
        }

        public static proiectSCcsharp.domain.Bilet GetBilet(AppResponse response)
        {
            proiectSCcsharp.domain.Bilet ticket = new proiectSCcsharp.domain.Bilet
            (
                response.Bilet.Id,
                response.Bilet.CumparatorName,
                response.Bilet.IdSpectacol,
                response.Bilet.NrSeats
            );
            return ticket;
        }

        public static proiectSCcsharp.domain.Spectacol GetSpectacol(AppResponse response)
        {
            proiectSCcsharp.domain.Spectacol spectacol = new proiectSCcsharp.domain.Spectacol
                (
                    response.Spectacol.Id,
                    response.Spectacol.ArtistName,
                    response.Spectacol.Date,
                    response.Spectacol.Time,
                    response.Spectacol.Place,
                    response.Spectacol.AvailableSeats,
                    response.Spectacol.SoldSeats
                );
            return spectacol;
        }

        public static List<proiectSCcsharp.domain.Spectacol> getAllSpectacole(AppResponse response)
        {
            List<proiectSCcsharp.domain.Spectacol> showDTOList = new List<proiectSCcsharp.domain.Spectacol>();
            foreach (var showDTO in response.Spectacole)
            {
                showDTOList.Add(new proiectSCcsharp.domain.Spectacol
                (
                    showDTO.Id,
                    showDTO.ArtistName,
                    showDTO.Date,
                    showDTO.Time,
                    showDTO.Place,
                    showDTO.AvailableSeats,
                    showDTO.SoldSeats
                ));
            }
            return showDTOList;
        }

        public static long getIdSpectacol(AppRequest request)
        {
            return request.IdSpectacol;
        }

        public static string getError(AppResponse response)
        {
            return response.Error;
        }

    }
}
