using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addEmployeeAndEquipmentDataCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "data",
                table: "equipment",
                maxLength: 2000,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "data",
                table: "employee",
                maxLength: 2000,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "data",
                table: "equipment");

            migrationBuilder.DropColumn(
                name: "data",
                table: "employee");
        }
    }
}
