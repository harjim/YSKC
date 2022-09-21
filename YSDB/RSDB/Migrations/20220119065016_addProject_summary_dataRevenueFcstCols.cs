using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addProject_summary_dataRevenueFcstCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "k69900",
                table: "c_rd_summary",
                newName: "c69900");

            migrationBuilder.RenameColumn(
                name: "k50000",
                table: "c_rd_summary",
                newName: "c50000");

            migrationBuilder.RenameColumn(
                name: "k40200",
                table: "c_rd_summary",
                newName: "c40200");

            migrationBuilder.RenameColumn(
                name: "k40000",
                table: "c_rd_summary",
                newName: "c40000");

            migrationBuilder.RenameColumn(
                name: "k30000",
                table: "c_rd_summary",
                newName: "c30000");

            migrationBuilder.RenameColumn(
                name: "k20700",
                table: "c_rd_summary",
                newName: "c20700");

            migrationBuilder.RenameColumn(
                name: "k20600",
                table: "c_rd_summary",
                newName: "c20600");

            migrationBuilder.RenameColumn(
                name: "k20500",
                table: "c_rd_summary",
                newName: "c20500");

            migrationBuilder.RenameColumn(
                name: "k20300",
                table: "c_rd_summary",
                newName: "c20300");

            migrationBuilder.RenameColumn(
                name: "k20200",
                table: "c_rd_summary",
                newName: "c20200");

            migrationBuilder.RenameColumn(
                name: "k20100",
                table: "c_rd_summary",
                newName: "c20100");

            migrationBuilder.RenameColumn(
                name: "k20000",
                table: "c_rd_summary",
                newName: "c20000");

            migrationBuilder.RenameColumn(
                name: "k10100",
                table: "c_rd_summary",
                newName: "c10100");

            migrationBuilder.RenameColumn(
                name: "k10000",
                table: "c_rd_summary",
                newName: "c10000");

            migrationBuilder.AddColumn<int>(
                name: "accountType",
                table: "c_rd_summary",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "employeeAmount",
                table: "c_rd_summary",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "finaMode",
                table: "c_rd_summary",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "rdFee",
                table: "c_rd_summary",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "revenueFcst",
                table: "c_rd_summary",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "salesRdFee",
                table: "c_rd_summary",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "accountType",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "employeeAmount",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "finaMode",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "rdFee",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "revenueFcst",
                table: "c_rd_summary");

            migrationBuilder.DropColumn(
                name: "salesRdFee",
                table: "c_rd_summary");

            migrationBuilder.RenameColumn(
                name: "c69900",
                table: "c_rd_summary",
                newName: "k69900");

            migrationBuilder.RenameColumn(
                name: "c50000",
                table: "c_rd_summary",
                newName: "k50000");

            migrationBuilder.RenameColumn(
                name: "c40200",
                table: "c_rd_summary",
                newName: "k40200");

            migrationBuilder.RenameColumn(
                name: "c40000",
                table: "c_rd_summary",
                newName: "k40000");

            migrationBuilder.RenameColumn(
                name: "c30000",
                table: "c_rd_summary",
                newName: "k30000");

            migrationBuilder.RenameColumn(
                name: "c20700",
                table: "c_rd_summary",
                newName: "k20700");

            migrationBuilder.RenameColumn(
                name: "c20600",
                table: "c_rd_summary",
                newName: "k20600");

            migrationBuilder.RenameColumn(
                name: "c20500",
                table: "c_rd_summary",
                newName: "k20500");

            migrationBuilder.RenameColumn(
                name: "c20300",
                table: "c_rd_summary",
                newName: "k20300");

            migrationBuilder.RenameColumn(
                name: "c20200",
                table: "c_rd_summary",
                newName: "k20200");

            migrationBuilder.RenameColumn(
                name: "c20100",
                table: "c_rd_summary",
                newName: "k20100");

            migrationBuilder.RenameColumn(
                name: "c20000",
                table: "c_rd_summary",
                newName: "k20000");

            migrationBuilder.RenameColumn(
                name: "c10100",
                table: "c_rd_summary",
                newName: "k10100");

            migrationBuilder.RenameColumn(
                name: "c10000",
                table: "c_rd_summary",
                newName: "k10000");
        }
    }
}
