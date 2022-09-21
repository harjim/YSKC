using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class d_equipment_workHours : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "workHours",
                table: "p_equipment");

            migrationBuilder.AddColumn<int>(
                name: "workHours",
                table: "d_equipment",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "workHours",
                table: "d_equipment");

            migrationBuilder.AddColumn<int>(
                name: "workHours",
                table: "p_equipment",
                nullable: false,
                defaultValue: 0);
        }
    }
}
