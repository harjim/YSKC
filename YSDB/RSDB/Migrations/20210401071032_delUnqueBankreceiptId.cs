using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class delUnqueBankreceiptId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_t_investment_bankReceipt_bankReceiptId",
                table: "t_investment_bankReceipt");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_t_investment_bankReceipt_bankReceiptId",
                table: "t_investment_bankReceipt",
                column: "bankReceiptId",
                unique: true);
        }
    }
}
