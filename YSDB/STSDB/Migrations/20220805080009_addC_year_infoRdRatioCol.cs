using Microsoft.EntityFrameworkCore.Migrations;

namespace STSDB.Migrations
{
    public partial class addC_year_infoRdRatioCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<decimal>(
                name: "rdRatio",
                table: "c_year_info",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "rdRatio",
                table: "c_year_info");
        }
    }
}
