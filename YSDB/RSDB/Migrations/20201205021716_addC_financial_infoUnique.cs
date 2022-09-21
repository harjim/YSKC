using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addC_financial_infoUnique : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_c_financial_info_companyId_year_key",
                table: "c_financial_info",
                columns: new[] { "companyId", "year", "key" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_c_financial_info_companyId_year_key",
                table: "c_financial_info");
        }
    }
}
