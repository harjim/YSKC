using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addSalaryInsuranceConfigCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "insuranceConfig",
                table: "salaryConfig",
                maxLength: 2000,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "insuranceDetail",
                table: "d_salary",
                maxLength: 2000,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "insuranceConfig",
                table: "salaryConfig");

            migrationBuilder.DropColumn(
                name: "insuranceDetail",
                table: "d_salary");
        }
    }
}
