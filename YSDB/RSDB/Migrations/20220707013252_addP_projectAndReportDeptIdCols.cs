using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addP_projectAndReportDeptIdCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "deptIds",
                table: "p_report",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "deptId",
                table: "p_project",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "deptIds",
                table: "p_report");

            migrationBuilder.DropColumn(
                name: "deptId",
                table: "p_project");
        }
    }
}
