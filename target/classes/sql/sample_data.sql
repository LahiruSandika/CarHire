insert into User values
                     ("Hiruni", "Hiruni Fernando", "hiruni@email.com", "0761234567", md5("hiruni")),
                     ("Senal", "Senal Perera", "senal@email.com", "0771234567", md5("senal")),
                     ("Osanda", "osanda Jayasekara", "osanda@email.com", "0711234567", md5("osanda")),
                     ("Nadun", "Nadun Rodrigo", "nadun@email.com", "0701234567", md5("nadun"));

insert into Category values
                         ("Sedan", "Hiruni", null),
                         ("SUV", "Nadun", "Kasun"),
                         ("Coupe", "Osanda", null),
                         ("Hatchback", "Senal", null);

insert into Brand values
                      ("Toyota", "Hiruni", null),
                      ("Honda", "Senal", null),
                      ("BMW", "Nadun", null),
                      ("Mazda", "Senal", null);

insert into Model values
                      ("Allion", "Toyota", "Sedan", "Hiruni", null),
                      ("Premio", "Toyota", "Sedan", "Osanda", null),
                      ("Fit", "Honda", "Hatchback", "Nadun", null),
                      ("Mazda 3", "Mazda", "Sedan", "Hiruni", null),
                      ("Pruis", "Toyota", "Hatchback", "Hiruni", "Senal");