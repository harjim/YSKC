using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class AlterRootDeptIdToBranchDeptId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "rootDeptId",
                table: "branch_summary",
                newName: "branchDeptId");

            migrationBuilder.RenameIndex(
                name: "IX_branch_summary_deptId_year_rootDeptId",
                table: "branch_summary",
                newName: "IX_branch_summary_deptId_year_branchDeptId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "branchDeptId",
                table: "branch_summary",
                newName: "rootDeptId");

            migrationBuilder.RenameIndex(
                name: "IX_branch_summary_deptId_year_branchDeptId",
                table: "branch_summary",
                newName: "IX_branch_summary_deptId_year_rootDeptId");
        }
    }
}
