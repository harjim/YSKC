using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class p_equipment_workHours : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "usedId",
                table: "p_attendance");

            migrationBuilder.AddColumn<int>(
                name: "workHours",
                table: "p_equipment",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "workHours",
                table: "p_equipment");

            migrationBuilder.AddColumn<int>(
                name: "usedId",
                table: "p_attendance",
                nullable: false,
                defaultValue: 0);
        }
    }
}
