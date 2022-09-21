using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addnameEnglishColAndAuthToGrant : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "authNo",
                table: "e_intellectual_property",
                newName: "grantNo");

            migrationBuilder.AddColumn<string>(
                name: "nameEnglish",
                table: "e_user_info",
                maxLength: 30,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "nameEnglish",
                table: "e_user_info");

            migrationBuilder.RenameColumn(
                name: "grantNo",
                table: "e_intellectual_property",
                newName: "authNo");
        }
    }
}
