using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addServiceLogPath : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "auditOpinion",
                table: "serviceLog",
                maxLength: 500,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "filePath",
                table: "serviceLog",
                maxLength: 200,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "auditOpinion",
                table: "serviceLog");

            migrationBuilder.DropColumn(
                name: "filePath",
                table: "serviceLog");
        }
    }
}
