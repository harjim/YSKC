using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class p_attendance_month_personAttData : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "attData",
                table: "p_attendance_month",
                newName: "personAttData");

            migrationBuilder.AddColumn<string>(
                name: "hoursAttData",
                table: "p_attendance_month",
                maxLength: 500,
                nullable: false,
                defaultValue: "");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "hoursAttData",
                table: "p_attendance_month");

            migrationBuilder.RenameColumn(
                name: "personAttData",
                table: "p_attendance_month",
                newName: "attData");
        }
    }
}
