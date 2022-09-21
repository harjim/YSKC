using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addPatent_buying_demandYearCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "year",
                table: "patent_buying_demand",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_patent_buying_demand_customerId_year",
                table: "patent_buying_demand",
                columns: new[] { "customerId", "year" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_patent_buying_demand_customerId_year",
                table: "patent_buying_demand");

            migrationBuilder.DropColumn(
                name: "year",
                table: "patent_buying_demand");
        }
    }
}
