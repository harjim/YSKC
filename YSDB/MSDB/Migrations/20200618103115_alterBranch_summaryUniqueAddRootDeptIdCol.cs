using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterBranch_summaryUniqueAddRootDeptIdCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_branch_summary_deptId_year",
                table: "branch_summary");

            migrationBuilder.CreateIndex(
                name: "IX_branch_summary_deptId_year_rootDeptId",
                table: "branch_summary",
                columns: new[] { "deptId", "year", "rootDeptId" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_branch_summary_deptId_year_rootDeptId",
                table: "branch_summary");

            migrationBuilder.CreateIndex(
                name: "IX_branch_summary_deptId_year",
                table: "branch_summary",
                columns: new[] { "deptId", "year" },
                unique: true);
        }
    }
}
