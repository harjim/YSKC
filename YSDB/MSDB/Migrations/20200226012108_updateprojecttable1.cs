using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class updateprojecttable1 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "fDeptId",
                table: "project",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "fDistrict",
                table: "project",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "tDeptId",
                table: "project",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "tDistrict",
                table: "project",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "fDeptId",
                table: "project");

            migrationBuilder.DropColumn(
                name: "fDistrict",
                table: "project");

            migrationBuilder.DropColumn(
                name: "tDeptId",
                table: "project");

            migrationBuilder.DropColumn(
                name: "tDistrict",
                table: "project");
        }
    }
}
