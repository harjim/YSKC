using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addP_reportSalesRdFeeAndFinaModeCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "finaMode",
                table: "p_report",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "salesRdFee",
                table: "p_report",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "finaMode",
                table: "p_report");

            migrationBuilder.DropColumn(
                name: "salesRdFee",
                table: "p_report");
        }
    }
}
