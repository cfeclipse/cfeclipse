
this.UserID = -1;
this.CompanyID = -1;
this.Username = "";
this.Password = "";
this.Admin = 0;

this.check_exprs["UserID"] = "^[1-9][0-9]*$";
this.check_exprs["Username"] = "^[a-zA-Z0-9\.@-_]{6,20}$";
this.check_exprs["Password"] = "^[!a-zA-Z0-9\.@-\\""##':;&$(\\[\])_+=~\\|{}]{6,32}$";
this.check_exprs["Admin"] = "^[0-1]$";

this.Groups = StructNew();
this.Permissions = StructNew();
this.SecurityLevels = StructNew();

this.USER_CANNOTACCESSPROJECT = 1;

this._CachedFunctionForClassReload = this.Get;

this.ADMIN_ADMIN 	= 1;
this.ADMIN_GEO 		= 2;
this.ADMIN_MANAGER	= 4;
