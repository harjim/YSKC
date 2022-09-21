using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class d_salary_dayHours : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "dayHours",
                table: "p_attendance",
                nullable: false,
                defaultValue: 8);

            migrationBuilder.AddColumn<int>(
                name: "dayHours",
                table: "d_salary",
                nullable: false,
                defaultValue: 8);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "dayHours",
                table: "p_attendance");

            migrationBuilder.DropColumn(
                name: "dayHours",
                table: "d_salary");
        }
    }
}
