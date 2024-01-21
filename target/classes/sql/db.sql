create table Brand (
                       Brand varchar(50) not null,
                       Created_By varchar(255),
                       Updated_By varchar(255),
                       primary key (Brand)
);

create table Category (
                          Category varchar(50) not null,
                          Created_By varchar(255),
                          Updated_By varchar(255),
                          primary key (Category)
);

create table Model (
                       Model varchar(50) not null,
                       Brand varchar(50) not null,
                       Category varchar(50) not null,
                       Created_By varchar(255),
                       Updated_By varchar(255),
                       primary key (Model),
                       foreign key (Brand) references Brand(Brand)
                           on delete cascade
                           on update cascade,
                       foreign key (Category) references Category(Category)
                           on delete cascade
                           on update cascade
);

create table Car (
                     ID varchar(5) not null,
                     Vehicle_Number varchar(255),
                     Category varchar(50) not null,
                     Brand varchar(50) not null,
                     Model varchar(50) not null,
                     Year integer,
                     Daily_Rental double precision,
                     Availability varchar(255) not null,
                     Created_By varchar(255),
                     Updated_By varchar(255),
                     primary key (ID),
                     foreign key (Category) references Category(Category),
                     foreign key (Brand) references Brand(Brand),
                     foreign key (Model) references Model(Model)
);

create table Customer (
                          ID varchar(5) not null,
                          Title varchar(255),
                          First_Name varchar(100) not null,
                          Last_Name varchar(100) not null,
                          NIC varchar(5) not null,
                          Address varchar(255) not null,
                          City varchar(20) not null,
                          Province varchar(255) not null,
                          Email varchar(255),
                          Created_By varchar(255),
                          Updated_By varchar(255),
                          primary key (ID)
);

create table CustomerPhone (
                               Customer_ID varchar(5) not null,
                               phone varchar(255),
                               foreign key (Customer_ID) references Customer(ID)
);

create table Rent (
                      ID varchar(5) not null,
                      Customer_ID varchar(5) not null,
                      Car_ID varchar(5) not null,
                      From_Date datetime(6) not null,
                      To_Date datetime(6) not null,
                      Daily_Rental double precision not null,
                      Total double precision not null,
                      Refundable_Deposit double precision not null,
                      Advance_Payment double precision not null,
                      Returned_On datetime(6),
                      Days_Overdue integer,
                      Balance double precision,
                      is_Returned varchar(255) not null,
                      Created_By varchar(255) not null,
                      Updated_By varchar(255),
                      primary key (ID),
                      foreign key (Customer_ID) references Customer(ID),
                      foreign key (Car_ID) references Car(ID)
);

create table User (
                      Username varchar(50) not null,
                      Name varchar(50) not null,
                      Email varchar(50) not null,
                      Mobile varchar(50) not null,
                      Password varchar(50) not null,
                      primary key (Username)
);