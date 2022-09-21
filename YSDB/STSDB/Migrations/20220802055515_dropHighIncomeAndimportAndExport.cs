using Microsoft.EntityFrameworkCore.Migrations;

namespace STSDB.Migrations
{
    public partial class dropHighIncomeAndimportAndExport : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "highIncome",
                table: "c_year_info");

            migrationBuilder.RenameColumn(
                name: "importAndExport",
                table: "c_year_info",
                newName: "rdFiexdAssets");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "rdFiexdAssets",
                table: "c_year_info",
                newName: "importAndExport");

            migrationBuilder.AddColumn<decimal>(
                name: "highIncome",
                table: "c_year_info",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);
        }
    }
}
