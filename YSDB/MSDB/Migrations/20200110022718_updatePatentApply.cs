using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class updatePatentApply : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "companyId",
                table: "patent_apply");

            migrationBuilder.RenameColumn(
                name: "companyName",
                table: "patent_apply",
                newName: "applyName");

            migrationBuilder.RenameColumn(
                name: "companyAddress",
                table: "patent_apply",
                newName: "address");

            migrationBuilder.AddColumn<int>(
                name: "customerId",
                table: "patent_apply",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "customerId",
                table: "patent_apply");

            migrationBuilder.RenameColumn(
                name: "applyName",
                table: "patent_apply",
                newName: "companyName");

            migrationBuilder.RenameColumn(
                name: "address",
                table: "patent_apply",
                newName: "companyAddress");

            migrationBuilder.AddColumn<int>(
                name: "companyId",
                table: "patent_apply",
                nullable: false,
                defaultValue: 0);
        }
    }
}
