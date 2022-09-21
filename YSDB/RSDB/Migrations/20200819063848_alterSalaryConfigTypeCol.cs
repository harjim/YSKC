using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterSalaryConfigTypeCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "insuranceConfig",
                table: "salaryConfig");

            migrationBuilder.RenameColumn(
                name: "insuranceNumber",
                table: "salaryConfig",
                newName: "type");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "type",
                table: "salaryConfig",
                newName: "insuranceNumber");

            migrationBuilder.AddColumn<string>(
                name: "insuranceConfig",
                table: "salaryConfig",
                maxLength: 2000,
                nullable: true);
        }
    }
}
