using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class dropC_supportStartAndEndYearCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "endYear",
                table: "c_support");

            migrationBuilder.DropColumn(
                name: "startYear",
                table: "c_support");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "endYear",
                table: "c_support",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "startYear",
                table: "c_support",
                nullable: false,
                defaultValue: 0);
        }
    }
}
