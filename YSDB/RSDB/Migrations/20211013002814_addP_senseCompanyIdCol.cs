using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addP_senseCompanyIdCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_p_sense_type_preachDate",
                table: "p_sense");

            migrationBuilder.AddColumn<int>(
                name: "companyId",
                table: "p_sense",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_p_sense_companyId_type_preachDate",
                table: "p_sense",
                columns: new[] { "companyId", "type", "preachDate" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_p_sense_companyId_type_preachDate",
                table: "p_sense");

            migrationBuilder.DropColumn(
                name: "companyId",
                table: "p_sense");

            migrationBuilder.CreateIndex(
                name: "IX_p_sense_type_preachDate",
                table: "p_sense",
                columns: new[] { "type", "preachDate" },
                unique: true);
        }
    }
}
