using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addProject_summary_dataRevenueFcstCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "k69900",
                table: "project_summary_data",
                newName: "totalIncome");

            migrationBuilder.RenameColumn(
                name: "k50000",
                table: "project_summary_data",
                newName: "highTechIncome");

            migrationBuilder.RenameColumn(
                name: "k40200",
                table: "project_summary_data",
                newName: "c69900");

            migrationBuilder.RenameColumn(
                name: "k40000",
                table: "project_summary_data",
                newName: "c50000");

            migrationBuilder.RenameColumn(
                name: "k30000",
                table: "project_summary_data",
                newName: "c40200");

            migrationBuilder.RenameColumn(
                name: "k20700",
                table: "project_summary_data",
                newName: "c40000");

            migrationBuilder.RenameColumn(
                name: "k20600",
                table: "project_summary_data",
                newName: "c30000");

            migrationBuilder.RenameColumn(
                name: "k20500",
                table: "project_summary_data",
                newName: "c20700");

            migrationBuilder.RenameColumn(
                name: "k20300",
                table: "project_summary_data",
                newName: "c20600");

            migrationBuilder.RenameColumn(
                name: "k20200",
                table: "project_summary_data",
                newName: "c20500");

            migrationBuilder.RenameColumn(
                name: "k20100",
                table: "project_summary_data",
                newName: "c20300");

            migrationBuilder.RenameColumn(
                name: "k20000",
                table: "project_summary_data",
                newName: "c20200");

            migrationBuilder.RenameColumn(
                name: "k10100",
                table: "project_summary_data",
                newName: "c20100");

            migrationBuilder.RenameColumn(
                name: "k10000",
                table: "project_summary_data",
                newName: "c20000");

            migrationBuilder.AlterColumn<decimal>(
                name: "rdFunds",
                table: "project_summary_data",
                nullable: true,
                oldClrType: typeof(decimal),
                oldType: "decimal(18,2)",
                oldNullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "accountType",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "c10000",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "c10100",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "employeeAmount",
                table: "project_summary_data",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "finaMode",
                table: "project_summary_data",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "highTechCount",
                table: "project_summary_data",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "patentCnt",
                table: "project_summary_data",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "rdFee",
                table: "project_summary_data",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "revenueFcst",
                table: "project_summary_data",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "salesRdFee",
                table: "project_summary_data",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "accountType",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "c10000",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "c10100",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "employeeAmount",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "finaMode",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "highTechCount",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "patentCnt",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "rdFee",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "revenueFcst",
                table: "project_summary_data");

            migrationBuilder.DropColumn(
                name: "salesRdFee",
                table: "project_summary_data");

            migrationBuilder.RenameColumn(
                name: "totalIncome",
                table: "project_summary_data",
                newName: "k69900");

            migrationBuilder.RenameColumn(
                name: "highTechIncome",
                table: "project_summary_data",
                newName: "k50000");

            migrationBuilder.RenameColumn(
                name: "c69900",
                table: "project_summary_data",
                newName: "k40200");

            migrationBuilder.RenameColumn(
                name: "c50000",
                table: "project_summary_data",
                newName: "k40000");

            migrationBuilder.RenameColumn(
                name: "c40200",
                table: "project_summary_data",
                newName: "k30000");

            migrationBuilder.RenameColumn(
                name: "c40000",
                table: "project_summary_data",
                newName: "k20700");

            migrationBuilder.RenameColumn(
                name: "c30000",
                table: "project_summary_data",
                newName: "k20600");

            migrationBuilder.RenameColumn(
                name: "c20700",
                table: "project_summary_data",
                newName: "k20500");

            migrationBuilder.RenameColumn(
                name: "c20600",
                table: "project_summary_data",
                newName: "k20300");

            migrationBuilder.RenameColumn(
                name: "c20500",
                table: "project_summary_data",
                newName: "k20200");

            migrationBuilder.RenameColumn(
                name: "c20300",
                table: "project_summary_data",
                newName: "k20100");

            migrationBuilder.RenameColumn(
                name: "c20200",
                table: "project_summary_data",
                newName: "k20000");

            migrationBuilder.RenameColumn(
                name: "c20100",
                table: "project_summary_data",
                newName: "k10100");

            migrationBuilder.RenameColumn(
                name: "c20000",
                table: "project_summary_data",
                newName: "k10000");

            migrationBuilder.AlterColumn<decimal>(
                name: "rdFunds",
                table: "project_summary_data",
                type: "decimal(18,2)",
                nullable: true,
                oldClrType: typeof(decimal),
                oldNullable: true);
        }
    }
}
