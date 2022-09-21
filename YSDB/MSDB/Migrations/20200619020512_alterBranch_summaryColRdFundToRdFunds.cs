using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterBranch_summaryColRdFundToRdFunds : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "rdFundSum",
                table: "branch_summary",
                newName: "rdFundsSum");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "rdFundsSum",
                table: "branch_summary",
                newName: "rdFundSum");
        }
    }
}
